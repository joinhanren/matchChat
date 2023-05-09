package com.join.service.impl;

import com.join.entity.Activity;
import com.join.mapper.ActivityMapper;
import com.join.service.PublishService;
import com.join.util.ThreadLocalObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.join.vo.ActivityVo;
import com.join.vo.Result;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author join
 * @Description
 * @date 2023/4/12 15:15
 */
@Service
public class PublishServiceImpl implements PublishService {

    @Autowired
    private ActivityMapper activityMapper;

    /**
     * 上传图片
     *
     * @param img
     * @return
     */
    @Override
    public Result uploadImage(MultipartFile img,String name) {
        String path=getClass().getResource("/").getPath();
        File dir = new File(path+"static/illustration");
        String suffix=name.substring(name.lastIndexOf("."));
        String fileName= UUID.randomUUID().toString().replaceAll("-", "")+suffix;
        if (!dir.exists()){
            dir.mkdirs();//创建目录
        }
        try {
            File image = new File(dir.getCanonicalPath()+"/"+fileName);
            img.transferTo(image);
            return Result.success("/illustration/"+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发布动态
     *
     * @param activityVo
     * @return
     */
    @Override
    @Transactional
    public Result publishMoment(ActivityVo activityVo) {
        String id =(String) ThreadLocalObject.get();
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityVo,activity);
        activity.setUserid(Long.parseLong(id));
        activity.setCreateTime(System.currentTimeMillis());
        try {
            activityMapper.insertActivity(activity);
            return Result.success(true);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.success(false);
    }
}
