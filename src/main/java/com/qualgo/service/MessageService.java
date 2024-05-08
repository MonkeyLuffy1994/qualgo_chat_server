package com.qualgo.service;

import com.qualgo.model.ChatRoom;
import com.qualgo.model.Message;
import com.qualgo.model.UserInfo;
import com.qualgo.repository.MessageRepository;
import com.qualgo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    private ChatRoom chatRoom;
    private UserRepository userRepository;
    private MessageRepository messageRepository;

    public MessageService(ChatRoom chatRoom, UserRepository userRepository, MessageRepository messageRepository) {
        this.chatRoom = chatRoom;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public void sendMessageToChat(String userName, String message) {
        UserInfo userInfo = userRepository.findUserByUsername(userName);
        if(userInfo == null) {
            return;
        }
        Message messageObj = new Message();
        messageObj.setContent(message);
        messageObj.setRoomId(chatRoom.getId());
        messageObj.setUserInfo(userInfo);
        messageObj.setSentAt(LocalDateTime.now());
        messageRepository.save(messageObj);
    }

    public List<Message> getListMessageByUserIdInRoom(String userName) {
        UserInfo userInfo = userRepository.findUserByUsername(userName);
        if(userInfo == null) {
            return null;
        }
        return messageRepository.findAllByUserInfoAndRoomIdOrderBySentAt(userInfo, chatRoom.getId());
    }

    public List<Message> getAllMessageInRoom() {
        return messageRepository.findAllByRoomIdOrderBySentAt(chatRoom.getId());

    }

    public boolean deleteMessageByUserId(String userName, long messageId) {
        UserInfo userInfo = userRepository.findUserByUsername(userName);
        if(userInfo == null) {
            return false;
        }
        Message message = messageRepository.findByIdAndUserInfo(messageId, userInfo);
        if(message == null) {
            return false;
        }
        messageRepository.delete(message);
        return true;
    }

}
