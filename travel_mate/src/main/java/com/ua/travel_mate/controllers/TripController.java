package com.ua.travel_mate.controllers;

import com.ua.travel_mate.entities.Trip;
import com.ua.travel_mate.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping("/{id}")
    public Trip getTripById(@PathVariable("id") Integer tripId) {
        return tripService.getTripById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
    }

    @PostMapping
    public Trip createTrip(@RequestBody Trip trip) {
        return tripService.saveTrip(trip);
    }

    @PutMapping("/{id}")
    public Trip updateTrip(@PathVariable("id") Integer tripId, @RequestBody Trip trip) {
        if (!tripId.equals(trip.getId())) {
            throw new IllegalArgumentException("Trip ID in path must match the ID in the request body");
        }
        return tripService.saveTrip(trip);
    }

    @DeleteMapping("/{id}")
    public void deleteTrip(@PathVariable("id") Integer tripId) {
        tripService.deleteTrip(tripId);
    }

}
