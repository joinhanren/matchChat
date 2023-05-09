package com.join.service;

import com.join.vo.Result;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author join
 * @Description
 * @date 2023/4/13 14:48
 */
public interface MateService {
    /**
     * 匹配好友
     * @return
     */
    public Result search();

    /**
     * 添加好友
     * @param id
     * @return
     */
    public Result addFriend(String id);
}
