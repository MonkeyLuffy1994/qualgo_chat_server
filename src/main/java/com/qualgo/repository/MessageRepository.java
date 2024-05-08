package com.qualgo.repository;

import com.qualgo.model.Message;
import com.qualgo.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByUserInfoAndRoomIdOrderBySentAt(UserInfo userInfo, int roomId);
    List<Message> findAllByRoomIdOrderBySentAt(int roomId);
    Message findByIdAndUserInfo(long id, UserInfo userInfo);

}
