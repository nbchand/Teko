package com.ncit.teko.repository;

import com.ncit.teko.model.Trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepo extends JpaRepository<Trip,Integer> {
    
}
