package com.system.announcement.dtos.Authentication;

import com.system.announcement.models.User;

public record responseAuthenticationRecordDTO(
        String nome,
        String email,
        String type,
        String role,
        String token
){
    public responseAuthenticationRecordDTO(User user, String token){
        this(
                user.getName(),
                user.getEmail(),
                user.getType().getType(),
                user.getRole().getRole(),
                token
        );
    }
}
