package com.system.announcement.dtos.user;

import com.system.announcement.models.User;
import jakarta.validation.constraints.NotBlank;

public record UserBasicDTO(
        @NotBlank String email,
        @NotBlank String name
) {
    public UserBasicDTO(User user){
        this(user.getEmail(), user.getName());
    }
}
