package com.system.announcement.services;

import com.system.announcement.exceptions.CityNotFoundException;
import com.system.announcement.models.City;
import com.system.announcement.repositories.CityRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City getById(@NotNull UUID city) {
        return cityRepository.findById(city).orElseThrow(CityNotFoundException::new);
    }
}
