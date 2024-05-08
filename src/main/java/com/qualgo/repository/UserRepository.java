package com.qualgo.repository;

import com.qualgo.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findUserByUsername(String userName);
}
