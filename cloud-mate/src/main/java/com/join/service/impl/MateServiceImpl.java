package com.join.service.impl;

import com.join.entity.User;
import com.join.mapper.MateMapper;
import com.join.service.MateService;
import com.join.util.ThreadLocalObject;
import com.join.vo.Result;
import com.join.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Map;


/**
 * @author join
 * @Description
 * @date 2023/4/13 14:49
 */
@Service
public class MateServiceImpl implements MateService {

    @Autowired
    private MateMapper mateMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Result search() {
        User searchUser = null;
        String id = (String) ThreadLocalObject.get();
        User user = (User) redisTemplate.opsForValue().get("cache:" + id);
        int myCode = hashCode(user);
        Map<String, UserVo> entries = redisTemplate.opsForHash().entries("user:" + id);
        Map<String, User> online = redisTemplate.opsForHash().entries("online");
        Iterator<Map.Entry<String, User>> iterator = online.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, User> next = iterator.next();
            int i = hashCode(next.getValue());
            if ((i + myCode) == 100) {
                searchUser = next.getValue();
                break;
            }
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(searchUser, userVo);
        return Result.success(userVo);
    }

    /**
     * 添加好友
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Result addFriend(String id) {
        boolean status=false;
        String myID =(String) ThreadLocalObject.get();
        mateMapper.addFriend(Long.parseLong(myID),Long.parseLong(id));
        mateMapper.addFriend(Long.parseLong(id),Long.parseLong(myID));
        status=true;
        return Result.success(status);
    }

    /**
     * 匹配算法
     *
     * @param user
     * @return
     */
    public int hashCode(User user) {
        int hashCode = 0;
        //女
        if (user.getSex().equals("1")) {
            hashCode += 20;
        }
        //男
        else if (user.getSex().equals("0")) {
            hashCode += 80;
        } else if (user.getSex().equals("2")) {
            hashCode += 50;
        }

        return hashCode;
    }

}
