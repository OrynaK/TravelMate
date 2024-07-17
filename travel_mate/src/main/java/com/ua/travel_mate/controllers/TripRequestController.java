package com.ua.travel_mate.controllers;

import com.ua.travel_mate.entities.TripRequest;
import com.ua.travel_mate.services.TripRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trip-requests")
public class TripRequestController {
    private final TripRequestService tripRequestService;

    @Autowired
    public TripRequestController(TripRequestService tripRequestService) {
        this.tripRequestService = tripRequestService;
    }

    @GetMapping
    public List<TripRequest> getAllTripRequests() {
        return tripRequestService.getAllTripRequests();
    }

    @GetMapping("/{id}")
    public TripRequest getTripRequestById(@PathVariable("id") Integer tripRequestId) {
        return tripRequestService.getTripRequestById(tripRequestId)
                .orElseThrow(() -> new RuntimeException("Trip request not found"));
    }

    @PostMapping
    public TripRequest createTripRequest(@RequestBody TripRequest tripRequest) {
        return tripRequestService.saveTripRequest(tripRequest);
    }

    @PutMapping("/{id}")
    public TripRequest updateTripRequest(@PathVariable("id") Integer tripRequestId, @RequestBody TripRequest tripRequest) {
        if (!tripRequestId.equals(tripRequest.getId())) {
            throw new IllegalArgumentException("Trip request ID in path must match the ID in the request body");
        }
        return tripRequestService.saveTripRequest(tripRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteTripRequest(@PathVariable("id") Integer tripRequestId) {
        tripRequestService.deleteTripRequest(tripRequestId);
    }
}
