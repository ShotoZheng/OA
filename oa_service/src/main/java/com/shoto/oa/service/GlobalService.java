package com.shoto.oa.service;

import com.shoto.oa.entity.Employee;

public interface GlobalService {

    Employee login(String sn, String password);

    void changePassword(Employee employee);
}
