package com.qualgo.controller;

import com.qualgo.dto.MessageResponse;
import com.qualgo.model.Message;
import com.qualgo.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MessageController extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private final MessageService messageService;

    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        String userName = session.getPrincipal().getName();
        for (WebSocketSession socketSession : sessions) {
            if (!socketSession.getId().equals(session.getId())) {
                try {
                    String contentMessage = userName + ": " + message.getPayload();
                    socketSession.sendMessage(new TextMessage(contentMessage));
                } catch (Exception e) {
                    logger.error("Not send message to session is connect.");
                }
            }
        }
        // Save message.
        messageService.sendMessageToChat(session.getPrincipal().getName(), String.valueOf(message.getPayload()));
    }

    @PostMapping("/send-message")
    public ResponseEntity<MessageResponse<?>> sendMessageToRoomRest(@RequestBody Message message, Authentication authentication) {
        messageService.sendMessageToChat(authentication.getName(), message.getContent());
        return ResponseEntity.ok(MessageResponse.ofSuccess(message));
    }

    @GetMapping("/get-message-by-user")
    public ResponseEntity<MessageResponse<?>> getListMessageByUserAndRoom(Authentication authentication) {
        List<Message> messageList = messageService.getListMessageByUserIdInRoom(authentication.getName());
        return ResponseEntity.ok(MessageResponse.ofSuccess(messageList));
    }

    @GetMapping("/get-all-message-in-room")
    public ResponseEntity<MessageResponse<?>> getAllMessageInRoom() {
        List<Message> messageList = messageService.getAllMessageInRoom();
        return ResponseEntity.ok(MessageResponse.ofSuccess(messageList));
    }

    @DeleteMapping("/delete-message/{messageId}")
    public ResponseEntity<MessageResponse<?>> deleteMessageByUser(Authentication authentication,
                                                                  @PathVariable long messageId) {
        boolean isDelete = messageService.deleteMessageByUserId(authentication.getName(), messageId);
        if(isDelete) {
            return ResponseEntity.ok(MessageResponse.ofSuccess("Delete message is success."));
        } else {
            return ResponseEntity.ok(MessageResponse.ofSuccess("Delete message is fail."));
        }
    }

}
