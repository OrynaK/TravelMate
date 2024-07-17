package com.ua.travel_mate.controllers;

import com.ua.travel_mate.entities.Place;
import com.ua.travel_mate.services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
public class PlaceController {
    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public List<Place> getAllPlaces() {
        return placeService.getAllPlaces();
    }

    @GetMapping("/{id}")
    public Place getPlaceById(@PathVariable("id") Integer placeId) {
        return placeService.getPlaceById(placeId)
                .orElseThrow(() -> new RuntimeException("Place not found"));
    }

    @PostMapping
    public Place createPlace(@RequestBody Place place) {
        return placeService.savePlace(place);
    }

    @PutMapping("/{id}")
    public Place updatePlace(@PathVariable("id") Integer placeId, @RequestBody Place place) {
        if (!placeId.equals(place.getId())) {
            throw new IllegalArgumentException("Place ID in path must match the ID in the request body");
        }
        return placeService.savePlace(place);
    }

    @DeleteMapping("/{id}")
    public void deletePlace(@PathVariable("id") Integer placeId) {
        placeService.deletePlace(placeId);
    }

}
