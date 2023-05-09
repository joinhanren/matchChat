package com.join;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author join
 * @Description
 * @date 2023/4/1 11:10
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LifeApplication {
    public static void main(String[] args) {
        SpringApplication.run(LifeApplication.class,args);
    }
}
