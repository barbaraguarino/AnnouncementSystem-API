package com.system.announcement.dtos.Category;

import jakarta.validation.constraints.NotBlank;

public record requestCategoryRecordDTO(
        @NotBlank String name
){}
