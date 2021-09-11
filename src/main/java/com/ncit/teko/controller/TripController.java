package com.ncit.teko.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ncit.teko.functionality.StringHandler;
import com.ncit.teko.model.Trip;
import com.ncit.teko.model.User;
import com.ncit.teko.service.TripCreateService;
import com.ncit.teko.service.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TripController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private TripCreateService tripCreateService;


    @GetMapping("/my-trips")
    public String showMyTrips(HttpSession session, Model model){

        if(session.getAttribute("userId")==null){
            return "index";
        }
        int userId = (int)session.getAttribute("userId");
        User user = userProfileService.fetchUserByUserId(userId);
        model.addAttribute("username", user.getUsername());

        model.addAttribute("trips", tripCreateService.getTripsByUserId(userId));
        return "mytrips";
    }

    @PostMapping("/my-trips/create-trip")
    public String createTrip(HttpServletRequest request, HttpSession session) throws Exception{

        Trip trip = new Trip();

        int userId = (int)session.getAttribute("userId");

        String time = request.getParameter("Time");
        String destination = request.getParameter("destination");
        String departure = request.getParameter("departure");
        int price = Integer.parseInt(request.getParameter("price"));
        int seats = Integer.parseInt(request.getParameter("availableSeats"));
        char typeOfTrip = request.getParameter("typeOfTrip").charAt(0);


        trip.setTime(time);
        trip.setAvailableSeats(seats);
        trip.setDeparture(departure);
        trip.setDestination(destination);
        trip.setPrice(price);
        trip.setTypeOfTrip(typeOfTrip);
        trip.setUId(userId);

        if(typeOfTrip == 'O'){
            String date = request.getParameter("Date");
            trip.setDate(date);
            
            tripCreateService.createTrip(trip);
            return "redirect:/my-trips";
         }
        
        String csvDays = StringHandler.arrayToCsv(request.getParameterValues("days"));
        trip.setDays(csvDays);
        tripCreateService.createTrip(trip);
        return "redirect:/my-trips";
    }
}  
