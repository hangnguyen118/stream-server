package com.example.streamserver.service;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.LocalTime;

@Component
public class TextWebSocketHandle extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        session.sendMessage(new TextMessage("123 456"));
        var clientMessage = message.getPayload();
        System.out.println(clientMessage);

        if (clientMessage.startsWith("hello") || clientMessage.startsWith("greet")) {
            session.sendMessage(new TextMessage("Hello there!"));
        } else if (clientMessage.startsWith("time")) {
            var currentTime = LocalTime.now();
            session.sendMessage(new TextMessage(currentTime.toString()));
        } else {
            session.sendMessage(new TextMessage("Unknown command"));
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message){
        try {
            session.sendMessage(new TextMessage("ok i'm get message!"));
            var clientMessage = message.getPayload();
            System.out.println(message.toString());
            System.out.println(clientMessage.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
