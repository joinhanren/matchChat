package com.join.interceptor;

import com.join.util.JwtUtil;
import com.join.util.ThreadLocalObject;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author join
 * @Description  JWT拦截器
 * @date 2023/3/1 20:26
 */
@Component
public class JWTInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入jwt拦截器");
        String token = request.getHeader("Authorization");
        /**
         * 如果字符串里面的值为null， ""， "  "，那么返回值为false；否则为true
         */
        if (!StringUtils.hasText(token)){
            return true;
        }
        String userid=null;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        }catch (Exception e){
            e.printStackTrace();
        }
        ThreadLocalObject.put(userid);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalObject.remove();
    }
}
