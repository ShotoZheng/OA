package com.shoto.oa.service;

import com.shoto.oa.entity.ClaimVoucher;
import com.shoto.oa.entity.ClaimVoucherItem;
import com.shoto.oa.entity.DealRecord;

import java.util.List;

public interface ClaimVoucherService {

    //保存报销单，一个报销单带有多个明细
    void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    ClaimVoucher get(int id);

    //根据报销单编号获取明细
    List<ClaimVoucherItem> getItems(int cvid);

    //根据报销单编号获取处理记录
    List<DealRecord> getRecords(int cvid);

    //获取个人报销单
    List<ClaimVoucher> getForSelf(String sn);

    //获取待处理报销单
    List<ClaimVoucher> getForDeal(String sn);

    void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    void submit(int id);

    void deal(DealRecord dealRecord);
}
