package com.join.encoder;

import com.alibaba.fastjson2.JSON;
import com.join.entity.Message;
import org.springframework.context.annotation.Configuration;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author join
 * @Description   实体类编码器
 * @date 2022/11/7 14:48
 */
@Configuration
public class BaseModelEncoder implements Encoder.Text<Message>{

    @Override
    public String encode(Message message) throws EncodeException {
        return JSON.toJSONString(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
