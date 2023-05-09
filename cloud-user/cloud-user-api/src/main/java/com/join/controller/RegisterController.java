package com.join.controller;

import com.join.service.RegisterService;
import com.join.vo.RegisterVo;
import com.join.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author join
 * @Description  注册
 * @date 2023/2/24 15:38
 */
@RestController
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo){
        return registerService.register(registerVo);
    }

}
