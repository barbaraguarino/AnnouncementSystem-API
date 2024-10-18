package com.system.announcement.controllers;

import com.system.announcement.configuration.security.TokenService;
import com.system.announcement.dtos.requestAuthenticationRecordDTO;
import com.system.announcement.dtos.responseAuthenticationRecordDTO;
import com.system.announcement.models.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authentication")
public class AuthenticationController{

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid requestAuthenticationRecordDTO authenticationRecordDTO){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationRecordDTO.email(), authenticationRecordDTO.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.status(HttpStatus.OK).body(new responseAuthenticationRecordDTO((User) auth.getPrincipal(), token));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while attempting to login");
        }
    }
}
