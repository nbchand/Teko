package com.ncit.teko.service;

import java.util.List;

import com.ncit.teko.model.Trip;
import com.ncit.teko.model.User;
import com.ncit.teko.repository.TripRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TripCreateService {

    @Autowired
    private TripRepo tripRepo;

    public void createTrip(Trip trip){
        tripRepo.save(trip);
    }

    public List<Trip> getTripsByUserId(int uId){
        return tripRepo.findByuId(uId);
    }

}
