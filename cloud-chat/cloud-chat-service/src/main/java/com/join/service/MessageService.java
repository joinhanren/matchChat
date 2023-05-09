package com.join.service;

import com.join.vo.MessageVo;

/**
 * @author join
 * @Description
 * @date 2023/3/26 14:17
 */
public interface MessageService {
    /**
     * 发现消息
     * @param
     * @param messageVo
     */
    public void pretreatmentAndSend(MessageVo messageVo);
}
