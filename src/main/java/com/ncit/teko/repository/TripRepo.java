package com.ncit.teko.repository;

import java.util.List;

import com.ncit.teko.model.Trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepo extends JpaRepository<Trip,Integer> {
    List<Trip> findByuId(int uId);
    List<Trip> findByDepartureAndDestination(String departure, String destination);
}
