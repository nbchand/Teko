package com.ncit.teko.service;

import com.ncit.teko.repository.TripRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TripDeleteService {
    @Autowired
    TripRepo tripRepo;
    public void deleteTrip(int id){
        tripRepo.deleteById(id);
    }
}
