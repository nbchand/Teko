package com.ncit.teko.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.ncit.teko.model.Trip;
import com.ncit.teko.model.User;
import com.ncit.teko.service.TripCreateService;
import com.ncit.teko.service.TripDeleteService;
import com.ncit.teko.service.TripUpdateService;
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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/my-trips")
public class TripController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private TripCreateService tripCreateService;

    @Autowired
    private TripDeleteService tripDeleteService;

    @Autowired
    private TripUpdateService tripUpdateService;

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
    public ResponseEntity<String> createTrip(@RequestBody Map<String,String> json, HttpSession session){

        Trip trip = new Trip();

        int userId = (int)session.getAttribute("userId");
        String response = tripCreateService.sendResponse(json, userId);

        if(!response.equals("")){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        trip.setAvailableSeats(Integer.parseInt(json.get("availableSeats")));
        trip.setDeparture(json.get("departure"));
        trip.setDestination(json.get("destination"));

        try{
            trip.setTime(json.get("Time"));
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>("error occured while setting time",HttpStatus.OK);
        }


        trip.setPrice(Integer.parseInt(json.get("price")));
        trip.setTypeOfTrip(json.get("typeOfTrip").charAt(0));
        trip.setUId(userId);

        if(json.get("typeOfTrip").charAt(0) == 'O'){
            try{
                trip.setDate(json.get("date"));
            }catch(Exception e){
                System.out.println(e);
                return new ResponseEntity<>("error occured while setting date",HttpStatus.OK);
            }

            tripCreateService.createTrip(trip);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         }

        trip.setDays(json.get("days"));
        tripCreateService.createTrip(trip);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/getTrip")
    public ResponseEntity<Trip> getTripInfo(@RequestBody String id){
        Trip trip = tripCreateService.getById(Integer.parseInt(id));
        if(trip==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(trip, HttpStatus.OK);
    }

    @PostMapping("/delete-trip")
    @ResponseBody
    public String removeTrip(@RequestBody String id){
        if(tripCreateService.getById(Integer.parseInt(id))==null){
            return "";
        }
        try{
            tripDeleteService.deleteTrip(Integer.parseInt(id));
            return "success";
        }catch(Exception e){
            System.out.println(e);
            return "";
        }
    }

    @PostMapping("/edit-trip")
    public ResponseEntity<String> editTrip(@RequestBody Map<String,String> json, HttpSession session){
        Trip trip = new Trip();

        int userId = (int)session.getAttribute("userId");
        String response = tripCreateService.sendResponse(json, userId);

        if(!response.equals("")){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        trip.setAvailableSeats(Integer.parseInt(json.get("availableSeats")));
        trip.setDeparture(json.get("departure"));
        trip.setDestination(json.get("destination"));

        try{
            trip.setTime(json.get("Time"));
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>("error occured while setting time",HttpStatus.OK);
        }

        trip.setPrice(Integer.parseInt(json.get("price")));
        trip.setTypeOfTrip(json.get("typeOfTrip").charAt(0));
        trip.setUId(userId);

        if(json.get("typeOfTrip").charAt(0) == 'O'){
            try{
                trip.setDate(json.get("date"));
            }catch(Exception e){
                System.out.println(e);
                return new ResponseEntity<>("error occured while setting date",HttpStatus.OK);
            }
            tripUpdateService.updateTrip(trip, Integer.parseInt(json.get("id")));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         }

        trip.setDays(json.get("days"));
        tripUpdateService.updateTrip(trip, Integer.parseInt(json.get("id")));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}  
