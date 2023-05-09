package com.join.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author join
 * @Description
 * @date 2023/2/17 21:08
 */
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {
    private User user;

    /**
     * 权限信息列表
     */
    private List<String> permissions;


    @JSONField(serialize = false) //不序列化到redis 中
    private List<SimpleGrantedAuthority> authorities;

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities!=null){
            return authorities;
        }
        authorities=new ArrayList<>();
        for (String s: permissions) {
            authorities.add(new SimpleGrantedAuthority(s));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
