package com.ncit.teko.service;

import java.util.List;
import java.util.Map;

import com.ncit.teko.functionality.PatternMatcher;
import com.ncit.teko.model.Trip;
import com.ncit.teko.repository.TripRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TripCreateService {

    @Autowired
    private TripRepo tripRepo;

    public void createTrip(Trip trip){
        tripRepo.save(trip);
    }

    public List<Trip> getTripsByUserId(int uId){
        return tripRepo.findByuId(uId);
    }

    public Trip getById(int id){
        return tripRepo.findByTripId(id);
    }

    public String sendResponse(Map<String,String> json, int userId){

        String time = json.get("Time");
        String destination = json.get("destination");
        String departure = json.get("departure");

        if(!PatternMatcher.checkNumberPattern(json.get("price"))||!PatternMatcher.checkNumberPattern(json.get("availableSeats"))){
            return "invalid input";
        }

        if(time.equals("")||departure.equals("")||destination.equals("")||json.get("availableSeats").equals("")
            ||json.get("typeOfTrip").equals("")||json.get("price").equals("")){
            return "fill all the necessary input fields";
        }

        if(departure.equals(destination)){
            return "departure and destination cannot be same";
        }

        if(json.get("price").length()>6){
            return "please lower the ticket price";
        }

        if(json.get("availableSeats").length()>4){
            return "please enter available seats only upto 4 digits";
        }

        // int price = Integer.parseInt(json.get("price"));
        int seats = Integer.parseInt(json.get("availableSeats"));
        char typeOfTrip = json.get("typeOfTrip").charAt(0);

        if(seats==0){
            return "numbers of available seats must be greater than 0";
        }

        if(typeOfTrip == 'O'){
            String date = json.get("date");
            if(date.equals("")){
                return "fill all the necessary input fields";
            }
            return "";
         }
        
        String days = json.get("days");
        if(days.equals("")){
            return "fill all the necessary input fields";
        }
        return "";
    }

}
