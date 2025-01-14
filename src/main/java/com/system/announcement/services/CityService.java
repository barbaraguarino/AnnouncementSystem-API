package com.system.announcement.services;

import com.system.announcement.exceptions.CityNotFoundException;
import com.system.announcement.models.City;
import com.system.announcement.repositories.CityRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAll() {
        return cityRepository.findAllByOrderByName();
    }

    public City getById(@NotNull UUID city){

        var optionalCity = cityRepository.findById(city);

        if(optionalCity.isPresent())
            return optionalCity.get();

        throw new CityNotFoundException();
    }
}
