package com.join.service;

import com.join.vo.Result;
import com.join.vo.UserVo;


/**
 * @author join
 * @Description
 * @date 2023/2/22 16:52
 */
public interface UserService {

    /**
     * 验证用户名是否存在
     * @param username
     * @return
     */
    public boolean checkUserName(String username);

    /**
     * 验证邮箱是否注册过
     * @param email
     * @return
     */
    public Result checkEmail(String email);

    /**
     * 注册发送验证码
     * @param email
     * @return
     */
    public Result sendEmail(String email);

    /**
     * 获取用户好友别表
     * @return
     */
    public Result getFriends();


    /**
     * 获取当前好友信息
     * @param id
     * @return
     */
    public UserVo getUser(Long id);
}
