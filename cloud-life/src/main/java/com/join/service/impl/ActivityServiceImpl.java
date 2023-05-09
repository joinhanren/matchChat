package com.join.service.impl;

import com.join.entity.Activity;
import com.join.mapper.ActivityMapper;
import com.join.service.ActivityService;
import com.join.util.ThreadLocalObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.join.vo.ActivityVo;
import com.join.vo.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author join
 * @Description
 * @date 2023/4/2 10:30
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;
    /**
     * 好友的动态
     *
     * @param id
     * @return
     */
    @Override
    public Result friendsActivity(Long id) {
        List<Activity> activities = activityMapper.queryFriendActivity(id);
        return Result.success(entityToVo(activities));
    }

    /**
     * w我的动态
     *
     * @return
     */
    @Override
    public Result myActivity() {
        String id =(String) ThreadLocalObject.get();
        List<Activity> activities = activityMapper.queryMyActivity(Long.parseLong(id));
        return Result.success(entityToVo(activities));
    }


    private List<ActivityVo> entityToVo(List<Activity> activities){
        List<ActivityVo> activityVos=new ArrayList<>();
        for (Activity activity:activities) {
            ActivityVo activityVo = new ActivityVo();
            BeanUtils.copyProperties(activity,activityVo);
            activityVos.add(activityVo);
        }
        return activityVos;
    }
}
