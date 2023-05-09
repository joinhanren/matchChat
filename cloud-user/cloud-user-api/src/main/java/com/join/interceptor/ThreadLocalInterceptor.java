package com.join.interceptor;

import com.join.thread.ThreadLocalObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author join
 * @Description
 * @date 2023/3/1 15:01
 */
@Component
public class ThreadLocalInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalObject.remove();
    }
}
