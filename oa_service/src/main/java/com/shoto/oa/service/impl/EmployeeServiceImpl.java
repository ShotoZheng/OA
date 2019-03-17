package com.shoto.oa.service.impl;

import com.shoto.oa.dao.EmployeeDao;
import com.shoto.oa.entity.Employee;
import com.shoto.oa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 员工业务实现类
 * @Author 郑松涛
 * @Create 2019-03-15 15:11
 * @Since 1.0.0
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public void add(Employee employee) {
        //添加员工时给默认密码
        employee.setPassword("000000");
        employeeDao.insert(employee);
    }

    @Override
    public void edit(Employee employee) {
        employeeDao.update(employee);
    }

    @Override
    public void remove(String sn) {
        employeeDao.delete(sn);
    }

    @Override
    public Employee get(String sn) {
        return employeeDao.select(sn);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.selectAll();
    }
}

