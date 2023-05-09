package com.join.service.impl;

import com.join.service.SendMailService;
import com.join.util.CreateMailUtil;
import com.join.util.VerificationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author join
 * @Description
 * @date 2023/2/23 21:59
 */

@Service
public class SendMailServiceImpl implements SendMailService {
    @Value("${spring.mail.username}")
    private String sendMailer;
    //注入邮件工具类
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final Logger log = LoggerFactory.getLogger(SendMailServiceImpl.class);
    /**
     * 发送验证码
     *
     * @param email
     */
    @Override
    public boolean sendVerifyCode(String email) {
        boolean status=false;
        CreateMailUtil createMailUtil = new CreateMailUtil();
        createMailUtil.setSendMailer(sendMailer);
        String code = VerificationUtil.createVerifyCode();
        System.out.println(code);
        SimpleMailMessage MailMessage = createMailUtil.createSimpleMailMessage(email, code);
        try {
            javaMailSender.send(MailMessage);
            redisTemplate.opsForValue().set(email, code, 2, TimeUnit.MINUTES);
            status=true;
        } catch (Exception e) {
            log.info("邮件发送失败");
            status=false;
            e.printStackTrace();
        }
        return status;
    }
}
