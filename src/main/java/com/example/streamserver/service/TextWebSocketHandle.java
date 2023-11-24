package com.example.streamserver.service;

import com.example.streamserver.entity.AuthResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;

@Component
public class TextWebSocketHandle extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        session.sendMessage(new TextMessage("SERVER REP:" + message.getPayload()));
        var clientMessage = message.getPayload();
        System.out.println(clientMessage);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message){
        try {
            session.sendMessage(new TextMessage("ok! server has received the data!"));
            System.out.println(message.toString());
            session.sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
