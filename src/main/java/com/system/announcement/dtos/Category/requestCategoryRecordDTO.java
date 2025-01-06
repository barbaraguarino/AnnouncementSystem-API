package com.system.announcement.dtos.category;

import jakarta.validation.constraints.NotBlank;

public record requestCategoryRecordDTO(
        @NotBlank String name
){}
