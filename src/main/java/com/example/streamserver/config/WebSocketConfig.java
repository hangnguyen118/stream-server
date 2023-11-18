package com.example.streamserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
   @Autowired
   WebSocketHandler webSocketHandler;
   @Override
   public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
       registry.addHandler(webSocketHandler, "/api/auth/ws").setAllowedOrigins("http://localhost", "http://localhost:63342", "http://192.168.1.76", "http://10.13.129.31");
   }

   @Bean
   public ServletServerContainerFactoryBean createWebSocketContainer() {
      ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
      container.setMaxTextMessageBufferSize(500000);
      container.setMaxBinaryMessageBufferSize(500000);
      return container;
   }

}
