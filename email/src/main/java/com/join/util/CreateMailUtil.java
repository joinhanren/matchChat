package com.join.util;

import com.join.entity.Mail;
import com.join.service.impl.SendMailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author join
 * @Description
 * @date 2022/8/31 9:59
 */
public class CreateMailUtil {

    private String sendMailer;

    private static final Logger logger = LoggerFactory.getLogger(SendMailServiceImpl.class);

    public void checkMail(Mail mailRequest) {
        Assert.notNull(mailRequest,"邮件请求不能为空");
        Assert.notNull(mailRequest.getSendTo(), "邮件收件人不能为空");
        Assert.notNull(mailRequest.getSubject(), "邮件主题不能为空");
        Assert.notNull(mailRequest.getText(), "邮件收件人不能为空");
    }

    public SimpleMailMessage createSimpleMailMessage(Mail mailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        checkMail(mailRequest);
        System.out.println("====================="+sendMailer);
        //邮件发件人
        message.setFrom(sendMailer);
        //邮件收件人 1或多个
        message.setTo(mailRequest.getSendTo().split(","));
        //邮件主题
        message.setSubject(mailRequest.getSubject());
        //邮件内容
        message.setText(mailRequest.getText());
        //邮件发送时间
        message.setSentDate(new Date());

        logger.info("发送邮件成功:{}->{}",sendMailer,mailRequest.getSendTo());
        return message;
    }

    public SimpleMailMessage createSimpleMailMessage(String receiveEmail,String code) {
        Mail mailRequest = new Mail();
        mailRequest.setSendTo(receiveEmail);
        mailRequest.setSubject("个人博客|验证码");
        mailRequest.setText(VerificationUtil.createVerifyInfo(code));
        return createSimpleMailMessage(mailRequest);
    }

    public void setSendMailer(String sendMailer){
        this.sendMailer=sendMailer;
    }


}
