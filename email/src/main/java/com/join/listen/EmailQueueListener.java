package com.join.listen;

import com.join.config.RabbitConfig;
import com.join.service.SendMailService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author join
 * @Description 监听邮箱队列
 * @date 2023/2/23 21:14
 */
@Component
@RabbitListener(queues = RabbitConfig.emailQueue)
public class EmailQueueListener {

    @Autowired
    private SendMailService sendMailService;

    @RabbitHandler
    public void sendEmail(@Payload String message,
                          @Header(AmqpHeaders.DELIVERY_TAG) long tag,
                          Channel channel) {
        sendMailService.sendVerifyCode(message);
        try {
            channel.basicAck(tag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
