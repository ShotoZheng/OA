package com.shoto.oa.dao;

import com.shoto.oa.entity.DealRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealRecordDao {

    void insert(DealRecord dealRecord);

    //通过报销单编号查询多个处理记录
    List<DealRecord> selectByClaimVoucher(int cvid);
}
