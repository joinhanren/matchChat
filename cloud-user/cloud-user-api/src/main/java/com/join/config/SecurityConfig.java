package com.join.config;

import com.join.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author join
 * @Description
 * @date 2023/2/17 18:58
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/img/**","/js/**","/assets/**", "/css/**", "/avatar/**", "/util/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .formLogin().loginPage("/login.html")
//                .loginProcessingUrl("/user/login")
//                .and()
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //于登录接口 允许匿名访问
                .antMatchers("/error","/register.html","/activity.html","/login.html","/loading.html","/main.html",
                        "/user/login","/user/checkName/*","/user/ttt","/test/1","/mate.html",
                        "/user/checkEmail/*","/user/sendEmail/*","/user/register","/user/user").permitAll()
                //后台系统的其他接口都需要认证才能访问
                .anyRequest().authenticated();

        /**
         * 配置jwtAuthenticationTokenFilter，
         * 在UsernamePasswordAuthenticationFilter之前过滤
         */
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
