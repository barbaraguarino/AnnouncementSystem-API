package com.system.announcement.dtos.authentication;

import jakarta.validation.constraints.NotBlank;

public record requestAuthenticationRecordDTO(
        @NotBlank String email,
        @NotBlank String password
){}
