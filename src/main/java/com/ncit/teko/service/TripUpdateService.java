package com.ncit.teko.service;

import com.ncit.teko.model.Trip;
import com.ncit.teko.repository.TripRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TripUpdateService {
    @Autowired
    TripRepo tripRepo;

    public void updateTrip(Trip trip, int tripId){
        if(trip.getTypeOfTrip()=='R'){
            tripRepo.updateAsRegularTrip(trip.getAvailableSeats(), trip.getDays(), trip.getDeparture(),
            trip.getDestination(), trip.getPrice(), trip.getTime(), tripId);
        }else{
            tripRepo.updateAsOneOffTrip(trip.getAvailableSeats(), trip.getDate(), trip.getDeparture(),
            trip.getDestination(), trip.getPrice(), trip.getTime(), tripId);
        }
        
    }
}
