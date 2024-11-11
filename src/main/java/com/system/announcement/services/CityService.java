package com.system.announcement.services;

import com.system.announcement.models.City;
import com.system.announcement.repositories.CityRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City getOrSave(@NotNull String city){
        var optionalCity = cityRepository.findByName(city);
        if(optionalCity.isPresent()){
            return optionalCity.get();
        }else{
            var newCity = new City();
            newCity.setName(city);
            return cityRepository.save(newCity);
        }
    }

    public Set<City> getAll() {
        return new HashSet<>(cityRepository.findAll());
    }
}
