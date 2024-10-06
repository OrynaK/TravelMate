package com.ua.travel_mate.services;

import com.ua.travel_mate.entities.Place;
import com.ua.travel_mate.repositories.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    public Optional<Place> getPlaceById(Integer placeId) {
        return placeRepository.findById(placeId);
    }

    public Place savePlace(Place place) {
        return placeRepository.save(place);
    }

    public void deletePlace(Integer placeId) {
        placeRepository.deleteById(placeId);
    }

}
