package com.shoto.oa.service.impl;

import com.shoto.oa.dao.ClaimVoucherDao;
import com.shoto.oa.dao.ClaimVoucherItemDao;
import com.shoto.oa.dao.DealRecordDao;
import com.shoto.oa.dao.EmployeeDao;
import com.shoto.oa.entity.ClaimVoucher;
import com.shoto.oa.entity.ClaimVoucherItem;
import com.shoto.oa.entity.DealRecord;
import com.shoto.oa.entity.Employee;
import com.shoto.oa.global.Contant;
import com.shoto.oa.service.ClaimVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description: 报销单相关业务实现
 * @Author 郑松涛
 * @Create 2019-03-16 10:38
 * @Since 1.0.0
 */
@Service
public class ClaimVoucherServiceImpl implements ClaimVoucherService {

    @Autowired
    private ClaimVoucherDao claimVoucherDao;

    @Autowired
    private ClaimVoucherItemDao claimVoucherItemDao;

    @Autowired
    private DealRecordDao dealRecordDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setCreateTime(new Date());
        //处理人为当前表单的创建者
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        //设置报销单状态(新创建)
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherDao.insert(claimVoucher);

        for (ClaimVoucherItem item : items) {
            //设置明细的报销单id
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemDao.insert(item);
        }
    }

    @Override
    public ClaimVoucher get(int id) {
        return claimVoucherDao.select(id);
    }

    @Override
    public List<ClaimVoucherItem> getItems(int cvid) {
        return claimVoucherItemDao.selectByClaimVoucher(cvid);
    }

    @Override
    public List<DealRecord> getRecords(int cvid) {
        return dealRecordDao.selectByClaimVoucher(cvid);
    }

    @Override
    public List<ClaimVoucher> getForSelf(String sn) {
        return claimVoucherDao.selectByCreateSn(sn);
    }

    @Override
    public List<ClaimVoucher> getForDeal(String sn) {
        return claimVoucherDao.selectByNextDealSn(sn);
    }

    @Override
    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherDao.update(claimVoucher);

        //删除数据库中与items不具有相同id的明细
        List<ClaimVoucherItem> olds = claimVoucherItemDao.selectByClaimVoucher(claimVoucher.getId());
        for (ClaimVoucherItem old : olds) {
            boolean isHave = false;
            for (ClaimVoucherItem item : items) {
                if (item.getId() == old.getId()) {
                    isHave = true;
                    break;
                }
            }
            if (!isHave)
                claimVoucherItemDao.delete(old.getId());
        }
        //进行更新或插入操作
        for (ClaimVoucherItem item : items) {
            //这里必须设置报销单明细的报销单id，不然再插入报销单明细是会缺少报销单id
            item.setClaimVoucherId(claimVoucher.getId());
            //若与数据库有相同id的明细则进行更新，否则进行插入
            if (item.getId() != null && item.getId() > 0)
                claimVoucherItemDao.update(item);
            else
                claimVoucherItemDao.insert(item);
        }
    }

    @Override
    public void submit(int id) {
        ClaimVoucher claimVoucher = claimVoucherDao.select(id);
        //根据报销单的创建人id来获取对应的创建者对象
        Employee employee = employeeDao.select(claimVoucher.getCreateSn());
        //更改报销单状态（已提交）
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_SUBMIT);
        //待处理人为部门经理
        List<Employee> emps = employeeDao.selectByDepartmentAndPost(employee.getDepartmentSn(), Contant.POST_FM);
        Employee emp = emps.get(0);
        claimVoucher.setNextDealSn(emp.getSn());
        claimVoucherDao.update(claimVoucher);

        //保存处理记录
        DealRecord dealRecord = new DealRecord();
        dealRecord.setDealWay(Contant.DEAL_SUBMIT);
        dealRecord.setDealSn(employee.getSn());
        dealRecord.setClaimVoucherId(id);
        dealRecord.setDealResult(Contant.CLAIMVOUCHER_SUBMIT);
        dealRecord.setDealTime(new Date());
        dealRecord.setComment("无");
        dealRecordDao.insert(dealRecord);
    }

    @Override
    public void deal(DealRecord dealRecord) {
        //根据处理记录来获取报销单
        ClaimVoucher claimVoucher = claimVoucherDao.select(dealRecord.getClaimVoucherId());
        //获取处理人员
        Employee employee = employeeDao.select(dealRecord.getDealSn());
        dealRecord.setDealTime(new Date());

        //如果报销单的处理方式是通过，那么需要判断是审核还是复审
        if (dealRecord.getDealWay().equals(Contant.DEAL_PASS)) {
            //如果当前的报销总额小于5000或者当前处理人是总经理，那么不需要进行复审
            if (claimVoucher.getTotalAmount() <= Contant.LIMIT_CHECK ||
                    employee.getPost().equals(Contant.POST_GM)) {
                //设置为已审核
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_APPROVED);
                //设置财务为待处理人
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null, Contant.POST_CASHIER).get(0).getSn());
                dealRecord.setDealResult(Contant.CLAIMVOUCHER_APPROVED);
            } else {//如果需要复审，设置报销单状态为待复审并设置总经理为待处理人
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_RECHECK);
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null, Contant.POST_GM).get(0).getSn());

                dealRecord.setDealResult(Contant.CLAIMVOUCHER_RECHECK);
            }
        } else if (dealRecord.getDealWay().equals(Contant.DEAL_BACK)){
            //如果处理方式是打回
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_BACK);
            //设置待处理人为创建者
            claimVoucher.setNextDealSn(claimVoucher.getCreateSn());

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_BACK);
        } else if (dealRecord.getDealWay().equals(Contant.DEAL_REJECT)) {
            //如果处理方式是拒绝
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_TERMINATED);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_TERMINATED);
        } else if (dealRecord.getDealWay().equals(Contant.DEAL_PAID)) {
            //如果处理方式是以打款
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_PAID);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_PAID);
        }

        claimVoucherDao.update(claimVoucher);
        dealRecordDao.insert(dealRecord);
    }
}

