package com.system.announcement.dtos.City;

import jakarta.validation.constraints.NotBlank;

public record requestCityRecordDTO(
        @NotBlank String name
) {
}
