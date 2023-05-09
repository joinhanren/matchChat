package com.join.controller;

import com.join.feign.UserFeign;
import com.join.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.join.vo.Result;

/**
 * @author join
 * @Description
 * @date 2023/4/2 10:23
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {


    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserFeign userFeign;
    /**
     * 我好友的动态
     * @param id
     * @return
     */
    @GetMapping("/activityList")
    public Result friendsActivity(@RequestParam("id") String id){
       return activityService.friendsActivity(Long.parseLong(id));
    }


    /**
     * 根据id 获取用户信息
     * @param id
     * @return
     */
    @GetMapping("/user")
    public Result getUser(@RequestParam("id") String id){
        return Result.success(userFeign.getUser(id));
    }

    /**
     * 我的动态
     * @return
     */
    @GetMapping("/myTrends")
    public Result myActivity(){
        return activityService.myActivity();
    }
}
