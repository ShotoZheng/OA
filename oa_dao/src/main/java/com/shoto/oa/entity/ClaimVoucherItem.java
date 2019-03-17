package com.shoto.oa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description 报销单明细
 * @Author: 郑松涛
 * @CreateDate: 2019-03-15 13:54
 * @Since: 1.0
 */
@Getter
@Setter
@ToString
public class ClaimVoucherItem {
    private Integer id;

    private Integer claimVoucherId;

    private String item;//费用类型

    private Double amount;

    private String comment;//描述

}