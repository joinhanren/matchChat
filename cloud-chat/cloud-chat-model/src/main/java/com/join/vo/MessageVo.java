package com.join.vo;

import com.join.entity.Message;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author join
 * @Description
 * @date 2023/3/1 21:51
 */
@Data
public class MessageVo implements Serializable {
    //发送人的id
    private Long sendId;
    //信息
    private String message;
    //消息发送的时间
    private Long sendTime;
    //发送消息人的ip地址
    private String ip;
    //接受消息人的id
    private Long receiveId;
    //显示的位置
    private String position;
    //头像
    private String avatar;

    private String type;

}
