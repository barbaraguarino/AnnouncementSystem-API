package com.system.announcement.controllers;

import com.system.announcement.dtos.authentication.LoginDTO;
import com.system.announcement.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authentication")
public class AuthenticationController{

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDTO authenticationRecordDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.login(authenticationRecordDTO));
    }
}
