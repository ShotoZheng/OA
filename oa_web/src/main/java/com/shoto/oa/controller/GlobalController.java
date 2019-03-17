package com.shoto.oa.controller;

import com.shoto.oa.entity.Employee;
import com.shoto.oa.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @Description: 员工登录和密码修改控制
 * @Author 郑松涛
 * @Create 2019-03-15 22:12
 * @Since 1.0.0
 */
@Controller
public class GlobalController {

    @Autowired
    private GlobalService globalService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(HttpSession session, @RequestParam String sn, @RequestParam String password) {
        Employee employee = globalService.login(sn, password);
        //如果为空，则说明登录失败，重新登录
        if (employee == null)
            return "redirect:to_login";
        //登录成功后，需要记录在session中，需用于对非登录用户的拦截
        session.setAttribute("employee", employee);
        //跳到个人信息显示页面
        return "redirect:self";
    }

    //个人信息
    @RequestMapping("/self")
    public String self() {
        return "self";
    }

    @RequestMapping("/quit")
    public String quit(HttpSession session) {
        session.setAttribute("employee", null);
        return "redirect:to_login";
    }

    @RequestMapping("/to_change_password")
    public String toChangePassword() {
        return "change_password";
    }

    @RequestMapping("/change_password")
    public String changePassword(HttpSession session, @RequestParam String old, @RequestParam String new1, @RequestParam String new2) {
        //先从session中获取员工
        Employee employee = (Employee) session.getAttribute("employee");
        //校验旧密码
        if (employee.getPassword().equals(old)) {
            //校验两次输入的密码是否相同
            if (new1.equals(new2)) {
                employee.setPassword(new1);
                globalService.changePassword(employee);
                return "redirect:self";
            }
        }
        return "redirect:to_change_password";

    }
}

