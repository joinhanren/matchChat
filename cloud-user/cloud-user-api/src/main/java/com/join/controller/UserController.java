package com.join.controller;

import com.join.service.UserService;
import com.join.vo.Result;
import com.join.vo.UserVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author join
 * @Description  用户操作相关API
 * @date 2023/2/22 16:49
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 验证用户名是否存在
     * @param username
     * @return
     */
    @GetMapping("/checkName/{username}")
    public Result checkUserName(@PathVariable("username") String username) {
        boolean check = userService.checkUserName(username);
        if (check) {
            return new Result(200, "校验成功！", true);
        }
        return new Result(400, "校验失败！", false);
    }


    /**
     * 验证邮箱是否注册过
     * @param email
     * @return
     */
    @GetMapping("/checkEmail/{email}")
    public Result checkEmail(@PathVariable("email") String email){
        return userService.checkEmail(email);
    }


    /**
     * 发送验证码
     * @param email
     * @return
     */
    @GetMapping("/sendEmail/{email}")
    public Result sendEmail(@PathVariable String email){
        return  userService.sendEmail(email);
    }


    /**
     * 获取用户好友列表
     * @return
     */
    @GetMapping("/friends")
    @PreAuthorize("hasAuthority('system:user:friends')")
    public Result getFriends(){
        return  userService.getFriends();
    }


    @GetMapping("/user")
    public UserVo getUser(@RequestParam("id") String id){
        return userService.getUser(Long.parseLong(id));
    }


}
