package com.shoto.oa.controller;

import com.shoto.oa.entity.Department;
import com.shoto.oa.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description: 部门控制器
 * @Author 郑松涛
 * @Create 2019-03-15 15:37
 * @Since 1.0.0
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/list")
    public String list(Map<String, Object> map) {
        List<Department> departments = departmentService.getAll();
        map.put("list", departments);
        return "department_list";
    }

    @RequestMapping("/to_add")
    public String toAdd(Map<String, Object> map) {
        //带个空对象（form标签需要）
        map.put("department", new Department());
        return "department_add";
    }

    @RequestMapping("/add")
    public String add(Department department) {
        departmentService.add(department);
        return "redirect:list";
    }

    @RequestMapping(value = "/to_update", params = {"sn"})
    public String toUpdate(String sn, Map<String, Object> map) {
        //根据sn查询对应的部门
        map.put("department", departmentService.get(sn));
        return "department_update";
    }

    @RequestMapping(value = "/update")
    public String update(Department department) {
        departmentService.edit(department);
        return "redirect:list";
    }

    @RequestMapping(value = "/remove", params = {"sn"})
    public String remove(String sn) {
        departmentService.remove(sn);
        return "redirect:list";
    }

}

