package com.system.announcement.services;

import com.system.announcement.auxiliary.components.AuthDetails;
import com.system.announcement.dtos.authentication.requestAuthenticationRecordDTO;
import com.system.announcement.dtos.authentication.responseAuthenticationRecordDTO;
import com.system.announcement.dtos.user.UserDTO;
import com.system.announcement.infra.token.TokenService;
import com.system.announcement.models.User;
import com.system.announcement.repositories.UserRepository;
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
    private final UserRepository userRepository;
    private final AuthDetails authDetails;

    public UserService(AuthenticationManager authenticationManager, TokenService tokenService, UserRepository userRepository, AuthDetails authDetails) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.authDetails = authDetails;
    }

    public responseAuthenticationRecordDTO login(@Valid requestAuthenticationRecordDTO authenticationRecordDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationRecordDTO.email(), authenticationRecordDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return new responseAuthenticationRecordDTO((User) auth.getPrincipal(), token);
    }

    public User getUserByEmail(String email) {
        return (User) userRepository.findByEmail(email);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public UserDTO getUser() {
        var user = authDetails.getAuthenticatedUser();
        return new UserDTO(user);
    }
}
