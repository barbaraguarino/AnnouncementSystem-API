package com.system.announcement.dtos.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(
        @NotBlank String name
){}
