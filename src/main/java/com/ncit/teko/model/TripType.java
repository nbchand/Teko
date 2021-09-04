package com.ncit.teko.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import com.sun.istack.NotNull;


@Entity
public class TripType{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trip_type_id")
    private int tripTypeId;

    @NotNull
    @Column(name = "trip_type")
    private String typeOfTrip;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "single_trip_id", referencedColumnName = "single_trip_id")
    private OneOffTrip oneOffTrip;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "regular_trip_id", referencedColumnName = "regular_trip_id")
    private RegularTrip regularTrip;

    @OneToOne(mappedBy = "tripType")
    private Trip trip;

    public int getTripTypeId() {
        return this.tripTypeId;
    }

    public void setTripTypeId(int tripTypeId) {
        this.tripTypeId = tripTypeId;
    }

    public Trip getTrip() {
        return this.trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public OneOffTrip getOneOffTrip() {
        return this.oneOffTrip;
    }

    public void setOneOffTrip(OneOffTrip oneOffTrip) {
        this.oneOffTrip = oneOffTrip;
    }

    public RegularTrip getRegularTrip() {
        return this.regularTrip;
    }

    public void setRegularTrip(RegularTrip regularTrip) {
        this.regularTrip = regularTrip;
    }

    public String getTypeOfTrip() {
        return this.typeOfTrip;
    }

    public void setTypeOfTrip(String typeOfTrip) {
        this.typeOfTrip = typeOfTrip;
    }

}
