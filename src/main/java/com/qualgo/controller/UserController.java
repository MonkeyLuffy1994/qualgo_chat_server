package com.qualgo.controller;

import com.qualgo.dto.MessageResponse;
import com.qualgo.dto.UserRequest;
import com.qualgo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final UserService userDetailsService;

    public UserController(AuthenticationManager authenticationManager, UserService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponse<String>> loginAccount(@RequestBody UserRequest userRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUserName(), userRequest.getPassWord()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(userRequest.getUserName());
        String token = userDetailsService.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(MessageResponse.ofSuccess(token));
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> createAccount(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userDetailsService.registerAccount(userRequest));
    }

}
