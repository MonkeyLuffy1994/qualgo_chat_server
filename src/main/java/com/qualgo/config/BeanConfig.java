package com.qualgo.config;

import com.qualgo.model.ChatRoom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class BeanConfig {

    @Bean
    public ChatRoom createChatRoom() {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(1);
        chatRoom.setName("Main room chat ...!");
        chatRoom.setStatus(true);
        chatRoom.setCreateDate(LocalDateTime.now());
        return chatRoom;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
