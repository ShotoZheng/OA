package com.shoto.oa.dao;

import com.shoto.oa.entity.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-dao.xml"})
public class DepartmentDaoTest {

    @Resource
    private DepartmentDao departmentDao;

    @Test
    public void insertTest() {
        Department department = new Department();
        department.setSn("10004");
        department.setName("郑松涛");
        department.setAddress("广州");
        departmentDao.insert(department);
    }

    @Test
    public void updateTest() {
        Department department = new Department();
        department.setSn("10004");
        department.setName("郑松涛");
        department.setAddress("广州海珠区");
        departmentDao.update(department);
    }

    @Test
    public void selectTest() {
        Department department = departmentDao.select("10002");
        System.out.println(department);
    }

    @Test
    public void selectAllTest() {
        List<Department> departments = departmentDao.selectAll();
        for (Department d : departments) {
            System.out.println(d);
        }
    }
}
