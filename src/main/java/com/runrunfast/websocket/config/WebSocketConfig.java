package com.runrunfast.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * @version <p>V1.0</p>
 * @Author: <H2>james</H2>
 * @Description: <P>webSocker配置</P>
 * @Date: <P>CREATE IS 2018/9/21 14:52</P>
 * WebSocketHandlerAdapter 负责将 EchoHandler 处理类适配到 WebFlux 容器中；
 * SimpleUrlHandlerMapping 指定了 WebSocket 的路由配置；
 * 使用 map 指定 WebSocket 协议的路由，路由为 ws://localhost:8080/echo
 **/
@Configuration
public class WebSocketConfig {
    @Autowired
    @Bean
    public HandlerMapping webSocketMapping(final EchoHandler echoHandler) {
        /**
         * 使用 map 指定 WebSocket 协议的路由，路由为 ws://localhost:8080/echo
         **/
        final Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/echo", echoHandler);
        /**
         * SimpleUrlHandlerMapping 指定了 WebSocket 的路由配置
         **/
        final SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
        mapping.setUrlMap(map);
        return mapping;
    }
    /**
     * WebSocketHandlerAdapter 负责将 EchoHandler 处理类适配到 WebFlux 容器中
     * @return
     * */
    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
