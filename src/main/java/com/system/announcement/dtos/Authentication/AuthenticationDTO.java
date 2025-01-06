package com.system.announcement.dtos.authentication;

import com.system.announcement.models.User;

public record AuthenticationDTO(
        String nome,
        String email,
        String type,
        String role,
        String token
){
    public AuthenticationDTO(User user, String token){
        this(
                user.getName(),
                user.getEmail(),
                user.getType().getType(),
                user.getRole().getRole(),
                token
        );
    }
}
