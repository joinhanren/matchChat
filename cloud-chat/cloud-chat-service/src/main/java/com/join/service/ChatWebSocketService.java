package com.join.service;

import javax.websocket.Session;

/**
 * @author join
 * @Description
 * @date 2023/3/2 10:58
 */
public interface ChatWebSocketService {

    /**
     * 对聊天消息的转发和处理
     */
    public void forwardAndProcess(Session session, String message);
}
