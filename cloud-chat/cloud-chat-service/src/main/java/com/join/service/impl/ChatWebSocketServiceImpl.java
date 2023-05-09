package com.join.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.join.entity.Message;
import com.join.service.ChatWebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

/**
 * @author join
 * @Description
 * @date 2023/3/2 10:59
 */
@Service
public class ChatWebSocketServiceImpl implements ChatWebSocketService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 对聊天消息的转发和处理
     *
     * @param session
     * @param msg
     */
    @Override
    public void forwardAndProcess(Session session, String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        Long sendId = (Long) session.getUserProperties().get("online");
        Long receiveId = Long.parseLong(jsonObject.getString("receiveId"));
        Message message = new Message();
        message.setSendId(sendId);
        message.setMessage(jsonObject.getString("message"));
        message.setReceiveId(receiveId);
        message.setSendTime(Long.parseLong(jsonObject.getString("currentMilliseconds")));
//        UserVo userVo = (UserVo) redisTemplate.opsForHash().get("user:" + sendId,
//                "friend:" + receiveId);
        /**
         * 验证好友列表中是否存在接收消息者ID，防止前端串改js代码
         */
        if (redisTemplate.opsForHash().hasKey("user:" + sendId, "friend:" + receiveId)) {
            /**
             * 验证接收者是否在线
             */
            if (redisTemplate.opsForHash().hasKey("online", "user:" + receiveId)) {
                //在线
                try {
                    session.getBasicRemote().sendObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
            } else {
                //不在线
                message.setReceived(false);
            }
            mongoTemplate.insert(message, "message");
        }

    }
}
