package com.ncit.teko.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class RegularTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "regular_trip_id")
    private int regularTripId;
    
    @Column
    @ElementCollection
    @CollectionTable(name = "regular_trip_days",
                    joinColumns = @JoinColumn(name = "regular_trip_id"))
    private Set<String> days;

    @OneToOne(mappedBy = "regularTrip")
    private TripType tripType;

    public int getRegularTripId() {
        return this.regularTripId;
    }

    public void setRegularTripId(int regularTripId) {
        this.regularTripId = regularTripId;
    }

    public TripType getTripType() {
        return this.tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public Set<String> getDays() {
        return this.days;
    }

    public void setDays(Set<String> days) {
        this.days = days;
    }

}
