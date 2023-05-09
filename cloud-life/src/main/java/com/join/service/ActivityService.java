package com.join.service;

import com.join.vo.Result;

/**
 * @author join
 * @Description
 * @date 2023/4/2 10:28
 */
public interface ActivityService {
    /**
     * 好友的动态
     * @param id
     * @return
     */
    public Result friendsActivity(Long id);

    /**
     * w我的动态
     * @return
     */
    public Result myActivity();
}
