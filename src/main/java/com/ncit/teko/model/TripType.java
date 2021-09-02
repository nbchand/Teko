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
    private int id;

    @NotNull
    @Column(name = "trip_type")
    private String typeOfTrip;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "single_trip_id", referencedColumnName = "id")
    private OneOffTrip oneOffTrip;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "regular_trip_id", referencedColumnName = "id")
    private RegularTrip regularTrip;

    @OneToOne(mappedBy = "tripType")
    private Trip trip;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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
