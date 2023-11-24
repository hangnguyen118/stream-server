package com.example.streamserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalTime;

@RestController
public class WebSocketController {
    @MessageMapping("/ws")
    @SendTo("/ws")
    public String sendToClient(@Payload String message) {
        return new String("OK! connect sucessfully!!");
    }
}

