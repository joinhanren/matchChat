package com.join.service.impl;

import com.join.entity.User;
import com.join.service.CloseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author join
 * @Description
 * @date 2023/3/2 15:34
 */
@Service
public class CloseServiceService implements CloseService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 从在线列表中移除
     *
     * @param id
     */
    @Override
    public void removeOnlineHashMap(Long id) {
        User user= (User) redisTemplate.opsForHash().get("online","user:"+id);
        redisTemplate.opsForHash().delete("online","user:"+id);
        redisTemplate.opsForValue().set("cache:"+id,user,20, TimeUnit.MINUTES);
    }
}
