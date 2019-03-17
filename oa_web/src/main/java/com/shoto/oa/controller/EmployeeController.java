package com.shoto.oa.controller;

import com.shoto.oa.entity.Employee;
import com.shoto.oa.global.Contant;
import com.shoto.oa.service.DepartmentService;
import com.shoto.oa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Description: 员工控制器
 * @Author 郑松涛
 * @Create 2019-03-15 15:37
 * @Since 1.0.0
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/list")
    public String list(Map<String, Object> map) {
        map.put("list", employeeService.getAll());
        return "employee_list";
    }

    @RequestMapping("/to_add")
    public String toAdd(Map<String, Object> map) {
        //带个空对象（form标签需要）
        map.put("employee", new Employee());
        //需用于下拉列表显示
        map.put("dlist", departmentService.getAll());
        //获取所有职位
        map.put("plist", Contant.getPosts());
        return "employee_add";
    }

    @RequestMapping("/add")
    public String add(Employee employee) {
        employeeService.add(employee);
        return "redirect:list";
    }

    @RequestMapping(value = "/to_update", params = {"sn"})
    public String toUpdate(String sn, Map<String, Object> map) {
        //根据sn查询对应的员工
        map.put("employee", employeeService.get(sn));
        //需用于下拉列表显示
        map.put("dlist", departmentService.getAll());
        //获取所有职位
        map.put("plist", Contant.getPosts());
        return "employee_update";
    }

    @RequestMapping(value = "/update")
    public String update(Employee employee) {
        employeeService.edit(employee);
        return "redirect:list";
    }

    @RequestMapping(value = "/remove", params = {"sn"})
    public String remove(String sn) {
        employeeService.remove(sn);
        return "redirect:list";
    }

}

