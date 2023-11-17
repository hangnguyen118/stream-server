package com.example.streamserver.service;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalTime;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        session.sendMessage(new TextMessage("Unknown command"));
//        var clientMessage = message.getPayload();
//
//        if (clientMessage.startsWith("hello") || clientMessage.startsWith("greet")) {
//            session.sendMessage(new TextMessage("Hello there!"));
//        } else if (clientMessage.startsWith("time")) {
//            var currentTime = LocalTime.now();
//            session.sendMessage(new TextMessage(currentTime.toString()));
//        } else {
//
//            session.sendMessage(new TextMessage("Unknown command"));
//        }
    }
}
