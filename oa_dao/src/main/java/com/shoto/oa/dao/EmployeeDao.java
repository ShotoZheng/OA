package com.shoto.oa.dao;

import com.shoto.oa.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao {

    void insert(Employee employee);

    void update(Employee employee);

    void delete(String sn);

    Employee select(String sn);

    List<Employee> selectAll();

    //根据部门编号和职位获取员工
    List<Employee> selectByDepartmentAndPost(@Param("dsn") String dsn, @Param("post") String post);
}
