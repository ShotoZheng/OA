package com.shoto.oa.service.impl;

import com.shoto.oa.dao.EmployeeDao;
import com.shoto.oa.entity.Employee;
import com.shoto.oa.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 员工登录功能和修改密码
 * @Author 郑松涛
 * @Create 2019-03-15 22:05
 * @Since 1.0.0
 */
@Service
public class GlobalServiceImpl implements GlobalService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public Employee login(String sn, String password) {
        Employee employee = employeeDao.select(sn);
        //判断是否存在该用户并判断密码是否正确
        if (employee != null && employee.getPassword().equals(password)) {
            return employee;
        }
        return null;
    }

    @Override
    public void changePassword(Employee employee) {
        employeeDao.update(employee);
    }
}

