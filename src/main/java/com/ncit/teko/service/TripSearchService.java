package com.ncit.teko.service;

import java.util.ArrayList;
import java.util.List;

import com.ncit.teko.model.Trip;
import com.ncit.teko.model.User;
import com.ncit.teko.repository.TripRepo;
import com.ncit.teko.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TripSearchService {
    @Autowired
    TripRepo tripRepo;

    @Autowired
    UserRepo userRepo;

    public List<Trip> searchTrips(String departure, String destination){
        return tripRepo.findByDepartureAndDestination(departure, destination);
    }

    public List<User> getUsersByTrips(List<Trip> trips){
        List<User> users = new ArrayList<>();
        for(Trip trip: trips){
            users.add(userRepo.findUserByUserId(trip.getUId()));
        }
        return users;
    }
}
