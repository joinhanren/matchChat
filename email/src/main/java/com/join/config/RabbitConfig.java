package com.join.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author join
 * @Description
 * @date 2023/2/23 20:12
 */
@Configuration
public class RabbitConfig {
    private static final String emailExchange="emailExchange";
    public static final String emailQueue="emailQueue";
    private static final String routingKeySend="sendEmail";

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(emailExchange);
    }

    @Bean
    public Queue queueEmail(){
        return new Queue(emailQueue,true,false,false);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queueEmail()).to(directExchange()).with(routingKeySend);
    }

}
