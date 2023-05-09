package com.join.mapper;

import com.join.entity.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author join
 * @Description
 * @date 2023/4/2 10:31
 */
@Mapper
public interface ActivityMapper {
    /**
     * 查询好友的动态
     * @param id
     * @return
     */
    public List<Activity> queryFriendActivity(Long id);

    /**
     * 发布一条动态
     * @param activity
     * @return
     */
    public int insertActivity(Activity activity);

    /**
     * 查询我的动态
     * @param id
     * @return
     */
    public  List<Activity> queryMyActivity(Long id);
}
