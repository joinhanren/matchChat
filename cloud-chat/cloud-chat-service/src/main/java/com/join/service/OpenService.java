package com.join.service;

import javax.websocket.Session;

/**
 * @author join
 * @Description  建立全双工连接服务
 * @date 2023/3/2 14:27
 */
public interface OpenService {

    /**
     * 添加到在线列表中
     * @param id
     */
    public void addOnlineHashMap(Long id);
}
