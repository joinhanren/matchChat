package com.join.service.impl;

import com.join.entity.Emoji;
import com.join.entity.Message;
import com.join.entity.User;
import com.join.mapper.ChatMapper;
import com.join.service.ChatService;
import com.join.util.ThreadLocalObject;
import com.join.vo.MessageVo;
import com.join.vo.Result;
import com.join.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author join
 * @Description
 * @date 2023/3/1 21:21
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ChatMapper chatMapper;

    /**
     * 获取和当前对象的聊天记录
     *
     * @param currentChatID
     */
    @Override
    public Result getHistory(Long currentChatID) {
        Long id = Long.parseLong((String) ThreadLocalObject.get());
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("sendId").is(id).and("receiveId").is(currentChatID),
                Criteria.where("sendId").is(currentChatID).and("receiveId").is(id));
//        criteria.and("received").is(true);
        query.addCriteria(criteria);
        query.limit(20).with(Sort.by(Sort.Order.desc("sendTime")));//升序，获取最新的20条
        List<Message> messages = mongoTemplate.find(query, Message.class, "message");
        //逆序LIST集合
        Collections.reverse(messages);//
        return Result.success(getFormatHistoryList(id,currentChatID, messages));
    }

    /**
     * 获取表情
     *
     * @return
     */
    @Override
    public Result getEmoji() {
        List<Emoji> list = redisTemplate.opsForList().range("emojis", -1L, 0);
        redisTemplate.expire("emojis", 20, TimeUnit.MINUTES);
        if (list.size() == 0) {
            List<Emoji> emojis = chatMapper.queryAllEmoji();
            redisTemplate.opsForList().leftPushAll("emojis", emojis);
            redisTemplate.expire("emojis", 20, TimeUnit.MINUTES);
            return Result.success(emojis);
        }
        return Result.success(list);
    }

    /**
     * 上传语音
     * @param file
     * @return
     */
    @Override
    public Result uploadAudio(MultipartFile file,String fileName){
        boolean status=false;
        System.out.println(getClass().getResource("/").getPath());
        String path=getClass().getResource("/").getPath();
        File dir = new File(path+"static/audio");
        if (!dir.exists()){
            dir.mkdirs();//创建目录
        }
        try {
            File audio = new File(dir.getCanonicalPath()+"/"+fileName);
            file.transferTo(audio);
            status=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
       return Result.success(status);
    }

    /**
     * 发送文件
     *
     * @param file
     * @param name
     * @return
     */
    @Override
    public Result uploadFile(MultipartFile file, String name) {
        String path=getClass().getResource("/").getPath();
        File dir = new File(path+"static/chatFile");
        String suffix=name.substring(name.lastIndexOf("."));
        String fileName= UUID.randomUUID().toString().replaceAll("-", "")+suffix;
        if (!dir.exists()){
            dir.mkdirs();//创建目录
        }
        try {
            File chatFile = new File(dir.getCanonicalPath()+"/"+fileName);
            file.transferTo(chatFile);
            return Result.success("/chatFile/"+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(false);
    }


    /**
     * 当前好友是否在线
     *
     * @param id
     */
    @Override
    public Result isOnline(Long id) {
        User user = (User) redisTemplate.opsForHash().get("online", "user:" +id);
        if (Objects.isNull(user)){
            return Result.success(false);
        }
        return Result.success(true);
    }

    /**
     * 格式化聊天历史记录
     *
     * @param myId
     * @param messages
     * @return
     */
    public List<MessageVo> getFormatHistoryList(Long myId, Long receiveId,List<Message> messages) {
        /**
         * 如果有历史聊天记录
         */
        if (messages.size() > 0) {
            UserVo my = (UserVo) redisTemplate.opsForHash().get("user:" + myId, "friend:" + myId);
            UserVo receive = (UserVo) redisTemplate.opsForHash().get("user:" + myId, "friend:" + receiveId);
            List<MessageVo> histories = new ArrayList<>();
            for (Message message : messages) {
                MessageVo messageVo = new MessageVo();
                messageVo.setMessage(message.getMessage());
                messageVo.setSendId(message.getSendId());
                messageVo.setIp(message.getIp());
                messageVo.setSendTime(message.getSendTime());
                messageVo.setReceiveId(message.getReceiveId());
                messageVo.setType(message.getType());
                if (message.getSendId().equals(myId)) {
                    messageVo.setPosition("right");
                    messageVo.setAvatar(my.getAvatar());
                } else {
                    messageVo.setPosition("left");
                    messageVo.setAvatar(receive.getAvatar());
                }
                histories.add(messageVo);
            }
            return histories;
        }

        return null;
    }

}
