package com.join.service.impl;

import com.join.entity.User;
import com.join.service.OpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;



/**
 * @author join
 * @Description
 * @date 2023/3/2 14:30
 */
@Service
public class OpenServiceImpl implements OpenService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加到在线列表中
     *
     * @param id
     */
    @Override
    public void addOnlineHashMap(Long id) {
        User user=(User) redisTemplate.opsForValue().get("cache:" + id);

        System.out.println(user);
        redisTemplate.opsForHash().put("online", "user:" + id, user);
        /**
         * 解决网突然掉线问题
         */
        redisTemplate.delete("cache:" + id);
    }



//    public User mate(String id){
//        User user=(User) redisTemplate.opsForValue().get("cache:" + id);
//        //计算用户信息权重
//        int weight= weight(user);
//        List<User> result=new ArrayList<>();
//        List<User> list= redisTemplate.opsForHash().put("online", "user:" + id, user);
//        for (User us: list) {
//            if (weight<(weight(us)+5)||weight>(weight(us)-5)){
//                result.add(us);
//            }
//        }
//        if (result.size()!=0){
//            //匹配成功，返回第一用户信息   
//            return result.get(0);
//        }else {
//            //匹配失败
//            return null;
//        }
//    }

}
