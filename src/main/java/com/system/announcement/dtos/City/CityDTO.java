package com.system.announcement.dtos.city;

import jakarta.validation.constraints.NotBlank;

public record CityDTO(
        @NotBlank String name
) {
}
