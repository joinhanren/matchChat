package com.join.websocket;

import com.alibaba.fastjson2.JSONObject;
import com.join.config.WebSocketConfig;
import com.join.encoder.BaseModelEncoder;
import com.join.service.CloseService;
import com.join.service.MessageService;
import com.join.service.OpenService;
import com.join.util.OnlineSession;
import com.join.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author join
 * @Description 聊天websocket
 * @date 2023/2/27 17:42
 */
@ServerEndpoint(value = "/chat", encoders = {BaseModelEncoder.class}, configurator = WebSocketConfig.class)
@Component
public class ChatWebSocketClient {

    //每个客户端对应一个ChatWebSocket
//    public static Map<Long, ChatWebSocketClient> onlineUsers = new ConcurrentHashMap<>();
//
//    private Session session;

    private static OpenService openService;
    @Autowired
    public  void  setOpenService(OpenService openService){
        ChatWebSocketClient.openService=openService;
    }

    private static CloseService closeService;
    @Autowired
    public void  setCloseService(CloseService closeService){
        ChatWebSocketClient.closeService=closeService;
    }

    private static MessageService messageService;
    @Autowired
    public void setMessageService(MessageService messageService){
        ChatWebSocketClient.messageService=messageService;
    }

    @OnOpen
    public void onOpen(Session session) {
        Long id = (Long) session.getUserProperties().get("online");
//        onlineUsers.put(id, this);
        System.out.println("========"+id);
        openService.addOnlineHashMap(id);
        OnlineSession.putSession(id,session);
//        this.session = session;
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println(message);
        JSONObject messageObject = JSONObject.parseObject(message);
        MessageVo messageVo=new MessageVo();
        messageVo.setMessage(messageObject.getString("message"));
        messageVo.setSendTime((Long) messageObject.get("currentMilliseconds"));
        messageVo.setReceiveId(((Integer) messageObject.get("receiveId")).longValue());
        messageVo.setType(messageObject.getString("type"));
        messageVo.setSendId((Long) session.getUserProperties().get("online"));

        messageService.pretreatmentAndSend(messageVo);

    }


    @OnClose
    public void onClose(Session session) {
        Long id = (Long) session.getUserProperties().get("online");
        closeService.removeOnlineHashMap(id);
//        onlineUsers.remove(id);
        OnlineSession.removeSession(id);
        System.out.println("关闭连接");
    }


//    @OnError
//    public void onError(Session session,Throwable throwable){
//        System.out.println("出现错误");
//    }

}
