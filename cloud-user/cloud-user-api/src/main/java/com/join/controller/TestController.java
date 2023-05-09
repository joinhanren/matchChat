package com.join.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author join
 * @Description
 * @date 2023/2/16 13:25
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/1")
    public String test(){
        return "1111";
    }




}
