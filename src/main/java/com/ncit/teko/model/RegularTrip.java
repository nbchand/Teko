package com.ncit.teko.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class RegularTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column
    private ArrayList<String> days;

    @OneToOne(mappedBy = "regularTrip")
    private TripType tripType;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TripType getTripType() {
        return this.tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public ArrayList<String> getDays() {
        return this.days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    
}
