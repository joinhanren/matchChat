package com.join.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author join
 * @Description 跨域响应头设置，解决跨域问题
 * @date 2023/3/26 11:10
 */
//@Component
public class CrossOriginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入过滤器");
        HttpServletResponse response=(HttpServletResponse) servletResponse;
//        if (((HttpServletRequest) servletRequest).getMethod().equals("OPTIONS")) {
//            System.out.println("opsion");
//            response.addHeader("Access-Control-Allow-Origin", "*");
//            response.addHeader("Access-Control-Allow-Credentials", "true");
//            response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
//            response.addHeader("Access-Control-Allow-Headers", "*");
//        }
        filterChain.doFilter(servletRequest, response);


    }
}
