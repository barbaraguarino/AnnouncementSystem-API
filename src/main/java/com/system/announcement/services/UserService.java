package com.system.announcement.services;

import com.system.announcement.dtos.Announcement.AnnouncementDTO;
import com.system.announcement.dtos.Authentication.requestAuthenticationRecordDTO;
import com.system.announcement.dtos.Authentication.responseAuthenticationRecordDTO;
import com.system.announcement.infra.token.TokenService;
import com.system.announcement.models.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public UserService(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public responseAuthenticationRecordDTO login(@Valid requestAuthenticationRecordDTO authenticationRecordDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationRecordDTO.email(), authenticationRecordDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return new responseAuthenticationRecordDTO((User) auth.getPrincipal(), token);
    }
}
