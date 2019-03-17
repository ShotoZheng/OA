package com.shoto.oa.dao;

import com.shoto.oa.entity.ClaimVoucherItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimVoucherItemDao {

    void insert(ClaimVoucherItem claimVoucherItem);

    void update(ClaimVoucherItem claimVoucherItem);

    void delete(int id);

    //根据报销单编号来获取多个报销单明细
    List<ClaimVoucherItem> selectByClaimVoucher(int cvid);
}
