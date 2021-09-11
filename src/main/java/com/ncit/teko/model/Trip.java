package com.ncit.teko.model;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import com.sun.istack.NotNull;


@Entity
public class Trip {

    private static final long serialVersionUID = 100L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trip_id")
    private int tripId;

    @NotNull
    @Column
    private String departure;

    @NotNull
    @Column
    private String destination;

    @NotNull
    @Column
    private int price;

    @NotNull
    @Column(name = "available_seats")
    private int availableSeats;

    @NotNull
    @Column
    private Time time;

    @Column
    private Date date;

    // @Column
    // @ElementCollection
    // @CollectionTable(name = "regular_trip_days",
    //                 joinColumns = @JoinColumn(name = "regular_trip_id"))
    // private List<String> days;

    @Column
    private String days;

    @NotNull
    @Column(name = "trip_type")
    private char typeOfTrip;

    @Column(name = "user_user_id")
    private int uId;


    public int getTripId() {
        return this.tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getDeparture() {
        return this.departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getAvailableSeats() {
        return this.availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Time getTime() {
        return this.time;
    }

    public void setTime(String time) throws Exception {
        DateFormat formatter = new SimpleDateFormat("hh:mm");
        this.time = new Time(formatter.parse(time).getTime());
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(String date) throws Exception {
        java.util.Date utilDate = new SimpleDateFormat("EEE d MMM, yyyy").parse(date);
        this.date = new java.sql.Date(utilDate.getTime());
    }

    public String getDays() {
        return this.days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public char getTypeOfTrip() {
        return this.typeOfTrip;
    }

    public void setTypeOfTrip(char typeOfTrip) {
        this.typeOfTrip = typeOfTrip;
    }

    public int getUId() {
        return this.uId;
    }

    public void setUId(int uId) {
        this.uId = uId;
    }

}
