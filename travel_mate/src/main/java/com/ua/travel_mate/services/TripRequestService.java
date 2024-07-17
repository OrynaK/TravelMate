package com.ua.travel_mate.services;

import com.ua.travel_mate.entities.TripRequest;
import com.ua.travel_mate.repositories.TripRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripRequestService {
    private final TripRequestRepository tripRequestRepository;

    @Autowired
    public TripRequestService(TripRequestRepository tripRequestRepository) {
        this.tripRequestRepository = tripRequestRepository;
    }

    public List<TripRequest> getAllTripRequests() {
        return tripRequestRepository.findAll();
    }

    public Optional<TripRequest> getTripRequestById(Integer tripRequestId) {
        return tripRequestRepository.findById(tripRequestId);
    }

    public TripRequest saveTripRequest(TripRequest tripRequest) {
        return tripRequestRepository.save(tripRequest);
    }

    public void deleteTripRequest(Integer tripRequestId) {
        tripRequestRepository.deleteById(tripRequestId);
    }

}
