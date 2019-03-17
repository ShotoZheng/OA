package com.shoto.oa.global;

import com.shoto.oa.entity.Employee;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 登录拦截器，防止未登录用户进行非法操作
 * @Author 郑松涛
 * @Create 2019-03-15 22:22
 * @Since 1.0.0
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //进行拦截操作
        //过滤掉那些可能与登录有关的URL请求
        String url = request.getRequestURI();
        if (url.toLowerCase().indexOf("login") >= 0)
            return true;

        //获取员工信息
        Employee employee = (Employee) request.getSession().getAttribute("employee");
        if (employee != null)//说明已经登录过，直接放行
            return true;

        //登录失败，跳转到登录页面
        response.sendRedirect("/to_login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

