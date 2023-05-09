package com.join.service;

import org.springframework.web.multipart.MultipartFile;
import com.join.vo.ActivityVo;
import com.join.vo.Result;

/**
 * @author join
 * @Description
 * @date 2023/4/12 15:14
 */

public interface PublishService {
    /**
     * 上传图片
     * @param img
     * @return
     */
    public Result uploadImage(MultipartFile img,String name);

    /**
     * 发布动态
     * @param activityVo
     * @return
     */
    public Result publishMoment(ActivityVo activityVo);
}
