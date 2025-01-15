package com.system.announcement.dtos.Authentication;

import jakarta.validation.constraints.NotBlank;

public record requestAuthenticationRecordDTO(
        @NotBlank String email,
        @NotBlank String password
){}
