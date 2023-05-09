package com.join.controller;

import com.join.service.LoginService;
import com.join.vo.LoginUserVo;
import com.join.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author join
 * @Description
 * @date 2023/2/16 14:32
 */
@Api("登录相关功能")
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginUserVo loginUser){
        System.out.println("---->"+loginUser);
        return  loginService.login(loginUser);
    }

    @GetMapping("/ttt")
    public String ttt(){
        return "123";
    }

}
