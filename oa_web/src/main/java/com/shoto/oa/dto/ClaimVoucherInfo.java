package com.shoto.oa.dto;

import com.shoto.oa.entity.ClaimVoucher;
import com.shoto.oa.entity.ClaimVoucherItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description:
 * @Author 郑松涛
 * @Create 2019-03-16 10:49
 * @Since 1.0.0
 */
@Getter
@Setter
public class ClaimVoucherInfo {

    private ClaimVoucher claimVoucher;

    private List<ClaimVoucherItem> items;
}

