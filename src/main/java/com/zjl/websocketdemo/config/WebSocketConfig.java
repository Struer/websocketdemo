package com.zjl.websocketdemo.config;

import com.zjl.websocketdemo.handler.MyHandler;
import com.zjl.websocketdemo.interceptor.WebSocketInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "myHandler/{ID}").setAllowedOrigins("*").addInterceptors(new WebSocketInterceptor());
    }
    public WebSocketHandler myHandler() {
        return new MyHandler();
    }

}