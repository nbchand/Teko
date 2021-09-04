package com.ncit.teko.controller;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.ncit.teko.model.Trip;
import com.ncit.teko.model.User;
import com.ncit.teko.service.TripService;
import com.ncit.teko.service.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TripController {
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private TripService tripService;

    @GetMapping("/my-trips")
    public String showMyTrips(HttpSession session, Model model){

        if(session.getAttribute("userId")==null){
            return "index";
        }
        int userId = (int)session.getAttribute("userId");
        User user = userProfileService.fetchUserByUserId(userId);
        model.addAttribute("username1", "@"+user.getUsername());
        return "mytrips";
    }

    @PostMapping("/my-trips/create-trip")
    public String createTrip(@RequestParam("days") String[] days, HttpServletRequest request) throws Exception{

        Trip trip = new Trip();

        String time = request.getParameter("Time");
        String destination = request.getParameter("destination");
        String departure = request.getParameter("departure");
        int price = Integer.parseInt(request.getParameter("price"));
        int seats = Integer.parseInt(request.getParameter("availableSeats"));
        String typeOfTrip = request.getParameter("typeOfTrip");

        trip.setTime(time);
        trip.setAvailableSeats(seats);
        trip.setDeparture(departure);
        trip.setDestination(destination);
        trip.setPrice(price);
        trip.setTypeOfTrip(typeOfTrip);

        if(request.getParameter("typeOfTrip").equals("oneOff")){
            String date = request.getParameter("Date");
            trip.setDate(date);
            tripService.createTrip(trip);
            return "redirect:/my-trips";
         }
        
        trip.setDays(Arrays.stream(days).collect(Collectors.toSet()));
        tripService.createTrip(trip);
        return "redirect:/my-trips";
    }
}  
