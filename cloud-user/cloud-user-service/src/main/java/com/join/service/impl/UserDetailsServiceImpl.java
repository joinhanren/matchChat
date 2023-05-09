package com.join.service.impl;

import com.join.entity.LoginUser;
import com.join.entity.User;
import com.join.mapper.LoginMapper;
import com.join.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author join
 * @Description
 * @date 2023/2/17 20:39
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = loginMapper.login(username);
        System.out.println(user);
        if (Objects.isNull(user)){
            throw new NullPointerException("用户名或密码错误！");
        }
        /**
         * 数据库获取用户权限信息列表
         */
        List<String> list = menuMapper.selectPermsByUserId(user.getId());
        return new LoginUser(user,list);
    }
}
