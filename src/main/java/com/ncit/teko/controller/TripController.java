package com.ncit.teko.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.ncit.teko.functionality.PatternMatcher;
import com.ncit.teko.model.Trip;
import com.ncit.teko.model.User;
import com.ncit.teko.service.TripCreateService;
import com.ncit.teko.service.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my-trips")
public class TripController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private TripCreateService tripCreateService;

    @GetMapping("")
    public String showMyTrips(HttpSession session, Model model){

        if(session.getAttribute("userId")==null){
            return "redirect:/";
        }
        int userId = (int)session.getAttribute("userId");
        User user = userProfileService.fetchUserByUserId(userId);
        model.addAttribute("username", user.getUsername());

        model.addAttribute("trips", tripCreateService.getTripsByUserId(userId));
        return "mytrips";
    }

    @PostMapping("/create-trip")
    public ResponseEntity<?> createTrip(@RequestBody Map<String,String> json, HttpSession session) throws Exception{

        Trip trip = new Trip();

        int userId = (int)session.getAttribute("userId");

        String time = json.get("Time");
        String destination = json.get("destination");
        String departure = json.get("departure");

        if(!PatternMatcher.checkNumberPattern(json.get("price"))||!PatternMatcher.checkNumberPattern(json.get("availableSeats"))){
            return new ResponseEntity<>("invalid input",HttpStatus.OK);
        }

        if(time.equals("")||departure.equals("")||destination.equals("")||json.get("availableSeats").equals("")
            ||json.get("typeOfTrip").equals("")||json.get("price").equals("")){
            return new ResponseEntity<>("fill all the necessary input fields",HttpStatus.OK);
        }

        if(departure.equals(destination)){
            return new ResponseEntity<>("departure and destination cannot be same",HttpStatus.OK);
        }

        if(json.get("price").length()>7){
            return new ResponseEntity<>("please lower the ticket price",HttpStatus.OK);
        }

        if(json.get("availableSeats").length()>4){
            return new ResponseEntity<>("please enter available seats only upto 4 digits",HttpStatus.OK);
        }

        int price = Integer.parseInt(json.get("price"));
        int seats = Integer.parseInt(json.get("availableSeats"));
        char typeOfTrip = json.get("typeOfTrip").charAt(0);

        if(seats==0){
            return new ResponseEntity<>("numbers of available seats must be greater than 0",HttpStatus.OK);
        }

        trip.setTime(time);
        trip.setAvailableSeats(seats);
        trip.setDeparture(departure);
        trip.setDestination(destination);
        trip.setPrice(price);
        trip.setTypeOfTrip(typeOfTrip);
        trip.setUId(userId);

        if(typeOfTrip == 'O'){
            String date = json.get("date");
            if(date.equals("")){
                return new ResponseEntity<>("fill all the necessary input fields",HttpStatus.OK);
            }
            trip.setDate(date);
            tripCreateService.createTrip(trip);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         }
        
        String days = json.get("days");
        if(days.equals("")){
            return new ResponseEntity<>("fill all the necessary input fields",HttpStatus.OK);
        }
        trip.setDays(days);
        tripCreateService.createTrip(trip);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}  
