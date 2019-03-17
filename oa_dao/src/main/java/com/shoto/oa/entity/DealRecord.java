package com.shoto.oa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description 处理记录类
 * @Author: 郑松涛
 * @CreateDate: 2019-03-15 13:49
 * @Since: 1.0
 */
@Getter
@Setter
@ToString
public class DealRecord {
    private Integer id;

    private Integer claimVoucherId;//报销单ID

    private String dealSn;//处理人编号

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date dealTime;

    private String dealWay;//处理类型

    private String dealResult;//处理结果

    private String comment;//备注

    private Employee dealer;

}