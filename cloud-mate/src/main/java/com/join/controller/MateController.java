package com.join.controller;

import com.join.service.MateService;
import com.join.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author join
 * @Description
 * @date 2023/4/13 14:46
 */
@RestController
@RequestMapping("/mate")
public class MateController {


    @Autowired
    private MateService mateService;

    @GetMapping("/search")
    public Result search(){
        return mateService.search();
    }

    /**
     * 添加好友
     * @param id
     * @return
     */
    @GetMapping("/addFriend")
    public Result addFriend(@RequestParam("id") String id){
       return mateService.addFriend(id);
    }

}
