package com.shoto.oa.service.impl;

import com.shoto.oa.dao.DepartmentDao;
import com.shoto.oa.entity.Department;
import com.shoto.oa.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 部门业务实现类
 * @Author 郑松涛
 * @Create 2019-03-15 15:11
 * @Since 1.0.0
 */

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public void add(Department department) {
        departmentDao.insert(department);
    }

    @Override
    public void edit(Department department) {
        departmentDao.update(department);
    }

    @Override
    public void remove(String sn) {
        departmentDao.delete(sn);
    }

    @Override
    public Department get(String sn) {
        return departmentDao.select(sn);
    }

    @Override
    public List<Department> getAll() {
        return departmentDao.selectAll();
    }
}

