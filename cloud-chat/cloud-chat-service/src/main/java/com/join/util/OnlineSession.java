package com.join.util;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author join
 * @Description  在线用户类
 * @date 2023/3/26 16:56
 */
public class OnlineSession {

    private static Map<Long, Session> onlineUsers = new ConcurrentHashMap<>();

    /**
     * 添加一个上线的用户
     * @param id
     * @param session
     */
    public static void putSession(Long id, Session session){
        onlineUsers.put(id,session);
    }

    /**
     * 移除一个在线用户
     * @param id
     */
    public static void removeSession(Long id){
        onlineUsers.remove(id);
    }

    /**
     * 获取在线用户的个数
     * @return
     */
    public static int getCount(){
        return onlineUsers.size();
    }

    /**
     * 获取一个用户的session
     * @param id
     * @return
     */
    public static Session getOneSession(Long id){
        return onlineUsers.get(id);
    }

}
