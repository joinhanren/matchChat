package com.join.entity;

import lombok.Data;

import java.util.List;

/**
 * @author join
 * @Description  消息实体类
 * @date 2022/11/4 17:02
 */
@Data
public class Message {
   //发送人的id
   private Long sendId;
   //信息
   private String message;
   //消息发送的时间
   private Long sendTime;
   //消息接收时间
 //  private LocalDateTime  receiveTime;
   //发送消息人的ip地址
   private String ip;
   //接受消息人的id
   private Long receiveId;
   //如果是群消息，群中所有接收者的id
   private List<Long> receiveList;
   //发送的信息是否被接收
   private Boolean received = true;
   /**
    * 消息的类型
    */
   private String type;

}
