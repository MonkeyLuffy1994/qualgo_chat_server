package com.qualgo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    private int id;
    private String name;
    private boolean status;
    private LocalDateTime createDate;
}
