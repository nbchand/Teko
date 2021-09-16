package com.ncit.teko.service;

import java.util.List;

import com.ncit.teko.model.Trip;
import com.ncit.teko.repository.TripRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TripSearchService {
    @Autowired
    TripRepo tripRepo;
    public List<Trip> searchTrips(String departure, String destination){
        return tripRepo.findByDepartureAndDestination(departure, destination);
    }
}
