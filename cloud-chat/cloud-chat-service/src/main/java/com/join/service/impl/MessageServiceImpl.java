package com.join.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.join.entity.Message;
import com.join.entity.User;
import com.join.service.MessageService;
import com.join.util.OnlineSession;
import com.join.vo.MessageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author join
 * @Description
 * @date 2023/3/26 14:17
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static Map<String, List<String>> rooms = new ConcurrentHashMap<>();

    /**
     * 发送消息
     *
     * @param
     * @param messageVo
     */
    private void send(MessageVo messageVo) {
        Message message = new Message();
        BeanUtils.copyProperties(messageVo, message);
        User user = (User) redisTemplate.opsForHash().get("online", "user:" + messageVo.getReceiveId());
        if (user != null) {
            User sendUser = (User) redisTemplate.opsForHash().get("online", "user:" + messageVo.getSendId());
            messageVo.setAvatar(sendUser.getAvatar());
            String msg = JSONObject.toJSONString(messageVo);
            //在线，发送消息，并且存储聊天记录
            try {
                OnlineSession.getOneSession(messageVo.getReceiveId()).getBasicRemote().sendText(msg);
                //存储消息
                message.setReceived(true);
            } catch (Exception e) {
                e.printStackTrace();
                message.setReceived(false);
            } finally {
                storage(message);
            }
        } else {
            //不在线，存储消息
            message.setReceived(false);
            storage(message);
        }
    }

    /**
     * 存储消息
     *
     * @param message
     */
    private void storage(Message message) {
        mongoTemplate.insert(message, "message");
    }


    /**
     * 消息预处理
     *
     * @param messageVo
     * @return
     */
    @Override
    public void pretreatmentAndSend(MessageVo messageVo) {
        if (messageVo.getType().equals("audio")) {
            messageVo.setMessage("/audio/" + messageVo.getMessage());
            send(messageVo);
        }else if(messageVo.getType().equals("text")){
            send(messageVo);
        } else if (messageVo.getType().equals("call")) {
            call(messageVo);
        }else if (messageVo.getType().equals("join")){
            join(messageVo);
        }else if (messageVo.getType().equals("candidate")){
            candidate(messageVo);
        }else if (messageVo.getType().equals("offer")){
            offer(messageVo);
        }else if(messageVo.getType().equals("answer")){
            answer(messageVo);
        }else if (messageVo.getType().equals("img")){
            send(messageVo);
        }

    }

    /**
     * 拨打，创建房间
     */
    private void call(MessageVo messageVo) {
        Long room = System.currentTimeMillis();
        List<String> list = new ArrayList<>();
        list.add(messageVo.getSendId().toString());
        rooms.put(room.toString(), list);
        messageVo.setMessage(room.toString());
        sendSign(messageVo);
    }

    private void join(MessageVo messageVo) {
        List<String> room = rooms.get(messageVo.getMessage());
        String s = room.get(0);
        messageVo.setType("live");
        Long receiveId= messageVo.getReceiveId();
        messageVo.setSendId(messageVo.getReceiveId());
        messageVo.setSendId(receiveId);
        String msg = getMsg(messageVo);
        try {
            OnlineSession.getOneSession(messageVo.getReceiveId()).getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        room.add(messageVo.getSendId().toString());

    }

    /**
     * 发送房间号
     */
    private void sendSign(MessageVo messageVo) {
        Long sendId = messageVo.getSendId();
        Long ReceiveId = messageVo.getReceiveId();
        String msg = null;
        try {
//            messageVo.setType("room");
//            msg = JSONObject.toJSONString(messageVo);
//            OnlineSession.getOneSession(sendId).getBasicRemote().sendText(msg);
            messageVo.setType("join");
            messageVo.setSendId(ReceiveId);
            messageVo.setReceiveId(sendId);
            msg = JSONObject.toJSONString(messageVo);
            OnlineSession.getOneSession(ReceiveId).getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void offer(MessageVo messageVo){
        Long receiveId= messageVo.getReceiveId();
        messageVo.setReceiveId(messageVo.getSendId());
        messageVo.setSendId(receiveId);
        try {
            OnlineSession.getOneSession(receiveId).getBasicRemote().sendText(getMsg(messageVo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void answer(MessageVo messageVo){
        try {
            OnlineSession.getOneSession(messageVo.getReceiveId()).getBasicRemote().sendText(getMsg(messageVo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void candidate(MessageVo messageVo){
        Long receiveId= messageVo.getReceiveId();
        messageVo.setReceiveId(messageVo.getSendId());
        messageVo.setSendId(receiveId);
        try {
            OnlineSession.getOneSession(receiveId).getBasicRemote().sendText(getMsg(messageVo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getMsg(MessageVo messageVo){
        return JSONObject.toJSONString(messageVo);
    }
}
