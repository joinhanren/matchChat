package com.join.controller;

import com.join.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.join.vo.ActivityVo;
import com.join.vo.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * @author join
 * @Description 发布动态
 * @date 2023/4/12 15:10
 */
@RestController
@RequestMapping("/publish")
public class PublishController {

    @Autowired
    private PublishService publishService;

    @PostMapping("/uploadImage")
    public Result uploadImage(MultipartFile file, HttpServletRequest request){
        return publishService.uploadImage(file,request.getParameter("name"));
    }

    /**
     * 发布动态
     * @return
     */
    @PutMapping("/publishMoment")
    public Result publishMoment(@RequestBody ActivityVo activityVo){
        System.out.println(activityVo);
        return publishService.publishMoment(activityVo);
    }
}
