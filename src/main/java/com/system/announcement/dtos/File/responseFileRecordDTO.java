package com.system.announcement.dtos.File;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record responseFileRecordDTO(
        @NotNull UUID id,
        @NotBlank String path
){}
