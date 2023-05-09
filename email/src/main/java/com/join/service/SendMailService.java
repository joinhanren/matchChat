package com.join.service;

/**
 * @author join
 * @Description
 * @date 2023/2/23 21:58
 */
public interface SendMailService {
    /**
     *发送验证码
     * @param email
     */
    public boolean sendVerifyCode(String email);
}
