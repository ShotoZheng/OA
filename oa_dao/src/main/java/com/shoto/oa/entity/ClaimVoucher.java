package com.shoto.oa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description 报销单
 * @Author: 郑松涛
 * @CreateDate: 2019-03-15 13:53
 * @Since: 1.0
 */
@Getter
@Setter
@ToString
public class ClaimVoucher {
    private Integer id;

    private String cause;//事由

    private String createSn;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date createTime;

    private String nextDealSn;//待处理人编号

    private Double totalAmount;

    private String status;//报销单状态

    private Employee creater;

    private Employee dealer;

}