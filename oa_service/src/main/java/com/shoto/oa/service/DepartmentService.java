package com.shoto.oa.service;

import com.shoto.oa.entity.Department;

import java.util.List;

public interface DepartmentService {

    void add(Department department);

    void edit(Department department);

    void remove(String sn);

    Department get(String sn);

    List<Department> getAll();
}
