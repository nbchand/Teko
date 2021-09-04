package com.ncit.teko.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OneOffTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "single_trip_id")
    private int singleTripId;

    @Column
    private Date date;

    @OneToOne(mappedBy = "oneOffTrip")
    private TripType tripType;

    public int getSingleTripId() {
        return this.singleTripId;
    }

    public void setSingleTripId(int singleTripId) {
        this.singleTripId = singleTripId;
    }

    public TripType getTripType() {
        return this.tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
