package com.join.config;


import com.join.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;


/**
 * @author join
 * @Description
 * @date 2023/2/27 17:43
 */
@Configuration
public class WebSocketConfig extends ServerEndpointConfig.Configurator {


    /**
     * 开启websocket支持
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * tcp 建立全双工连接前验证token
     *
     * @param sec
     * @param request
     * @param response
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {

        List<String> secWebSocketProtocol = request.getHeaders().get("Sec-WebSocket-Protocol");
        String token = secWebSocketProtocol.get(0);
        String id = null;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            id = claims.getSubject();
            System.out.println("++++++++"+id);
        } catch (Exception e) {
            //TODO token 解析异常处理，建立连接失败
        }
        sec.getUserProperties().put("online", Long.parseLong(id));
        /**
         * 校验jwt
         */

        ////在后端握手时设置一下请求头（Sec-WebSocket-Protocol），
        // 前端发来什么授权值，这里就设置什么值，不设置会报错导致建立连接成功后立即被关闭
        response.getHeaders().put("Sec-WebSocket-Protocol", secWebSocketProtocol);
        super.modifyHandshake(sec, request, response);
    }


}
