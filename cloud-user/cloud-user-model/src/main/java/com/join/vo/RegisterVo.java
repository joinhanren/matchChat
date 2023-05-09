package com.join.vo;

import lombok.Data;

/**
 * @author join
 * @Description
 * @date 2023/2/24 15:40
 */
@Data
public class RegisterVo {
    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 验证码
     */
    private String verification;
    /**
     * 密码
     */
    private String password;

}
