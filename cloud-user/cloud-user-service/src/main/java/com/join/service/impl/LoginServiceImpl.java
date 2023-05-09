package com.join.service.impl;

import com.alibaba.fastjson.JSON;
import com.join.entity.LoginUser;
import com.join.entity.User;
import com.join.entity.UserInfo;
import com.join.mapper.LoginMapper;
import com.join.service.LoginService;
import com.join.util.Encryption;
import com.join.util.JwtUtil;
import com.join.util.RedisCache;
import com.join.vo.LoginUserVo;
import com.join.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author join
 * @Description 登录service
 * @date 2023/2/16 17:32
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录
     *
     * @param loginUserVo
     * @return 返回token
     */
    @Override
    public Result login(LoginUserVo loginUserVo) {
        //AuthenticationManager 认证
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(loginUserVo.getUsername(), loginUserVo.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        //如果通过，用id生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        System.out.println("//////////" + loginUser);
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        //把用户信息存储到redis 中
        redisCache.setCacheObject("login:" + userid, loginUser, 30, TimeUnit.MINUTES);
        redisCache.setCacheObject("cache:"+userid,loginUser.getUser(),20,TimeUnit.MINUTES);
        return new Result(200, "登录成功！", map);
    }


}
