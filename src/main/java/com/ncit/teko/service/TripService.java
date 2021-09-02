package com.ncit.teko.service;

import com.ncit.teko.model.Trip;
import com.ncit.teko.repository.TripRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TripService {

    @Autowired
    private TripRepo tripRepo;

    public void createTrip(Trip trip){
        tripRepo.save(trip);
    }
}
