package com.qualgo.service;

import com.qualgo.common.JwtTokenProvider;
import com.qualgo.dto.MessageResponse;
import com.qualgo.dto.UserRequest;
import com.qualgo.model.UserInfo;
import com.qualgo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserInfo userInfo = userRepository.findUserByUsername(s);
        if(userInfo == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return new org.springframework.security.core.userdetails.User(s, userInfo.getPassword(), new ArrayList<>());
    }

    public MessageResponse registerAccount(UserRequest userRequest) {
        UserInfo userInfo = userRepository.findUserByUsername(userRequest.getUserName());
        if(userInfo != null) {
            return MessageResponse.ofError(HttpStatus.BAD_REQUEST);
        }
        userInfo = new UserInfo().setUsername(userRequest.getUserName()).setPassword(passwordEncoder.encode(userRequest.getPassWord()));
        userRepository.save(userInfo);
        return MessageResponse.ofSuccess();
    }

    public String generateToken(String userName) {
        return jwtTokenProvider.generateToken(userName);
    }
}
