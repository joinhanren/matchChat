package com.join.filter;

import com.join.thread.ThreadLocalObject;
import com.join.entity.LoginUser;
import com.join.util.JwtUtil;
import com.join.util.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author join
 * @Description jwt过滤器
 * @date 2023/2/18 22:02
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("进入user");
        System.out.println(request.getRequestURI());
        //获取token
        String token = request.getHeader("Authorization");
        //token为空
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        //解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法！");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(redisKey);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("未登录！");
        }
        //存储到securitycontextHolder中
        UsernamePasswordAuthenticationToken authentication = new
                UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ThreadLocalObject.put(loginUser);
        redisTemplate.opsForValue().set(redisKey,loginUser,30, TimeUnit.MINUTES);
        //放行
        filterChain.doFilter(request, response);

    }
}
