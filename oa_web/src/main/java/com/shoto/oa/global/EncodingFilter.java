package com.shoto.oa.global;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 编码过滤器，处理中文乱码的情况
 * @Author 郑松涛
 * @Create 2019-03-14 22:26
 * @Since 1.0.0
 */
public class EncodingFilter implements Filter {

    private String encoding = "utf-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //初始化使用编码初始化参数，如果没有指定则默认使用utf-8编码
        if (filterConfig.getInitParameter("encoding") != null) {
            encoding = filterConfig.getInitParameter("encoding");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //设置请求和响应编码
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

