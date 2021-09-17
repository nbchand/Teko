package com.ncit.teko.repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.ncit.teko.model.Trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepo extends JpaRepository<Trip,Integer> {
    List<Trip> findByuId(int uId);
    List<Trip> findByDepartureAndDestination(String departure, String destination);
    Trip findByTripId(int tripId);

    @Modifying(flushAutomatically = true)
    @Query(value = "update trip set available_seats = :as, date = :dt, days = null, departure = :dep, destination = :des , price = :pr, time = :tm, trip_type = 'O' where trip_id = :ti", nativeQuery = true)
    void updateAsOneOffTrip(@Param("as") int seats, @Param("dt") Date date, @Param("dep") String departure,
                            @Param("des") String destination, @Param("pr") int price, @Param("tm") Time time,
                            @Param("ti") int tripId);

    @Modifying(flushAutomatically = true)
    @Query(value = "update trip set available_seats = :as, date = null, days = :dy, departure = :dep, destination = :des , price = :pr, time = :tm, trip_type = 'R' where trip_id = :ti", nativeQuery = true)
    void updateAsRegularTrip(@Param("as") int seats, @Param("dy") String days, @Param("dep") String departure,
                            @Param("des") String destination, @Param("pr") int price, @Param("tm") Time time,
                            @Param("ti") int tripId); 
}
