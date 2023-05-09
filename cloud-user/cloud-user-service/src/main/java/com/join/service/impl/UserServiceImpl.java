package com.join.service.impl;

import com.join.entity.LoginUser;
import com.join.entity.User;
import com.join.mapper.UserMapper;
import com.join.service.UserService;
import com.join.thread.ThreadLocalObject;
import com.join.util.EntityCopyToVo;
import com.join.util.FormatCheck;
import com.join.util.RedisCache;
import com.join.vo.Result;
import com.join.vo.UserVo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author join
 * @Description
 * @date 2023/2/22 16:54
 */
@Service
public class UserServiceImpl implements UserService, RabbitTemplate.ConfirmCallback {

    @Autowired
    private UserMapper userMapper;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public UserServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
    }

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 验证用户名是否存在
     *
     * @param username
     * @return
     */
    @Override
    public boolean checkUserName(String username) {
        if (Objects.isNull(userMapper.checkUserName(username))) {
            return true;
        }
        return false;
    }

    /**
     * 验证邮箱是否注册过
     *
     * @param email
     * @return
     */
    @Override
    public Result checkEmail(String email) {
        String res = userMapper.checkEmail(email);
        if (Objects.isNull(res)) {
            return new Result(200, "允许注册", true);
        }
        return new Result(400, "该邮箱已注册！", false);
    }

    /**
     * 注册发送验证码
     *
     * @param email
     * @return
     */
    @Override
    public Result sendEmail(String email) {

        if (FormatCheck.checkEmail(email)) {
            if (Objects.isNull(userMapper.checkEmail(email))) {
//                Message message=new Message(email.getBytes());
                rabbitTemplate.convertAndSend("emailExchange", "sendEmail", email);
                return new Result(200, "验证码发送成功!", true);
            } else {
                return new Result(400, "该邮箱已注册！");
            }
        }
        return new Result(400, "邮箱格式错误！", false);
    }

    /**
     * 获取用户好友别表
     *
     * @return
     */
    @Override
    public Result getFriends() {
        LoginUser loginUser = (LoginUser) ThreadLocalObject.get();
        UserVo userVo = new UserVo();
        List<User> users = userMapper.selectFriendsById(loginUser.getUser().getId());
        List<UserVo> userVos = new ArrayList<>();
        Map<String, UserVo> map = new HashMap<>();
        for (User user : users) {
            UserVo userV = EntityCopyToVo.userToUserVo(user);
            if (user.getId().equals(loginUser.getUser().getId())) {
                userVo = userV;
            } else {
                userVos.add(userV);
            }
            map.put("friend:" + user.getId(), userV);
        }
        userVo.setFriends(userVos);
        // 获取的好友列表放入redis中
        redisTemplate.opsForHash().putAll("user:" + userVo.getId(), map);
        return new Result(200, "成功", userVo);
    }

    /**
     * 获取当前好友信息
     *
     * @param id
     * @return
     */
    @Override
    public UserVo getUser(Long id) {
        UserVo userVo = new UserVo();
//        User cacheUser = (User) redisTemplate.opsForHash().get("online", "user:" +id);
        User cacheUser = (User) redisTemplate.opsForValue().get("cache:"+id);
        if (Objects.isNull(cacheUser)){
            User user = userMapper.queryUserById(id);
            BeanUtils.copyProperties(user,userVo);
            return userVo;
        }
        BeanUtils.copyProperties(cacheUser,userVo);
        return userVo;
    }

    /**
     * 发布确认回调
     *
     * @param correlationData
     * @param b
     * @param s
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if (b) {
            //TODO 消息发布到队列成功！
        } else {
            //失败
        }
    }


}
