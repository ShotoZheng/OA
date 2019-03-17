package com.shoto.oa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description 员工类
 * @Author: 郑松涛
 * @CreateDate: 2019-03-15 13:49
 * @Since: 1.0
 */
@Getter
@Setter
@ToString
public class Employee {
    private String sn;  //员工ID

    private String password;

    private String name;

    private String departmentSn;//所属部门

    private String post;//职务

    private Department department;

}