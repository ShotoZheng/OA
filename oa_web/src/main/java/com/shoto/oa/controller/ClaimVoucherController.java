package com.shoto.oa.controller;

import com.shoto.oa.dto.ClaimVoucherInfo;
import com.shoto.oa.entity.DealRecord;
import com.shoto.oa.entity.Employee;
import com.shoto.oa.global.Contant;
import com.shoto.oa.service.ClaimVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Description: 报销单控制器
 * @Author 郑松涛
 * @Create 2019-03-16 10:53
 * @Since 1.0.0
 */
@Controller
@RequestMapping("/claim_voucher")
public class ClaimVoucherController {

    @Autowired
    private ClaimVoucherService claimVoucherService;

    @RequestMapping("/to_add")
    public String toAdd(Map<String, Object> map) {
        //保存费用类别
        map.put("items", Contant.getItems());
        map.put("info", new ClaimVoucherInfo());
        return "claim_voucher_add";
    }

    /**
     *  
     * @param  info 是SpringMVC自动封装的
     * @return 
     * @exception 
     * @date 2019-03-16 11:37
     */
    @RequestMapping("/add")
    public String add(HttpSession session, ClaimVoucherInfo info) {
        //从session中获取创建者对象
        Employee employee = (Employee) session.getAttribute("employee");
        //设置创建者编号
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherService.save(info.getClaimVoucher(), info.getItems());
        return "redirect:deal";
    }

    @RequestMapping("/deal")
    public String deal(HttpSession session, Map<String, Object> map) {
        //从session中获取创建者对象
        Employee employee = (Employee) session.getAttribute("employee");
        //获取创建者的所有报销单
        map.put("list",claimVoucherService.getForDeal(employee.getSn()));
        return "claim_voucher_deal";
    }

    @RequestMapping(value = "/detail")
    public String detail(int id, Map<String, Object> map) {
        map.put("claimVoucher", claimVoucherService.get(id));
        map.put("items", claimVoucherService.getItems(id));
        map.put("records", claimVoucherService.getRecords(id));
        return "claim_voucher_detail";
    }

    @RequestMapping("/self")
    public String self(HttpSession session, Map<String, Object> map) {
        //从session中获取创建者对象
        Employee employee = (Employee) session.getAttribute("employee");
        map.put("list",claimVoucherService.getForSelf(employee.getSn()));
        return "claim_voucher_self";
    }

    @RequestMapping("/to_update")
    public String toUpdate(int id, Map<String, Object> map) {
        //保存费用类别
        map.put("items", Contant.getItems());
        //用于回显
        ClaimVoucherInfo info = new ClaimVoucherInfo();
        info.setClaimVoucher(claimVoucherService.get(id));
        info.setItems(claimVoucherService.getItems(id));
        map.put("info", info);
        return "claim_voucher_update";
    }

    @RequestMapping("/update")
    public String update(HttpSession session, ClaimVoucherInfo info) {
        //从session中获取创建者对象
        Employee employee = (Employee) session.getAttribute("employee");
        //设置创建者编号
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherService.update(info.getClaimVoucher(), info.getItems());
        return "redirect:deal";
    }

    @RequestMapping("/submit")
    public String submit(int id) {
        claimVoucherService.submit(id);
        return "redirect:deal";
    }

    @RequestMapping("/to_check")
    public String toCheck(int id, Map<String, Object> map) {
        map.put("claimVoucher", claimVoucherService.get(id));
        map.put("items", claimVoucherService.getItems(id));
        map.put("records", claimVoucherService.getRecords(id));
        DealRecord dealRecord = new DealRecord();
        dealRecord.setClaimVoucherId(id);
        map.put("record", dealRecord);
        return "claim_voucher_check";
    }

    @RequestMapping("/check")
    public String check(HttpSession session, DealRecord dealRecord) {
        //从session中获取创建者对象
        Employee employee = (Employee) session.getAttribute("employee");
        dealRecord.setDealSn(employee.getSn());
        claimVoucherService.deal(dealRecord);
        return "redirect:deal";
    }


}

