package com.shoto.oa.dao;

import com.shoto.oa.entity.ClaimVoucher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimVoucherDao {

    void insert(ClaimVoucher claimVoucher);

    void update(ClaimVoucher claimVoucher);

    void delete(int id);

    ClaimVoucher select(int id);

    //根据创建人编号查询多个报销单记录
    List<ClaimVoucher> selectByCreateSn(String csn);

    //根据处理人编号来查询多个报销单记录
    List<ClaimVoucher> selectByNextDealSn(String ndsn);
}
