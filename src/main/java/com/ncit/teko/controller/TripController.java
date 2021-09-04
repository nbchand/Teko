package com.ncit.teko.controller;

import javax.servlet.http.HttpSession;

import com.ncit.teko.model.OneOffTrip;
import com.ncit.teko.model.RegularTrip;
import com.ncit.teko.model.Trip;
import com.ncit.teko.model.TripType;
import com.ncit.teko.model.User;
import com.ncit.teko.service.TripService;
import com.ncit.teko.service.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String createTrip(@ModelAttribute Trip trip, @ModelAttribute TripType tripType ,
                            @ModelAttribute RegularTrip regularTrip, //@ModelAttribute OneOffTrip oneOffTrip,
                            @RequestParam("Time") String time) throws Exception{

        tripType.setRegularTrip(regularTrip);
        //tripType.setOneOffTrip(oneOffTrip);

        trip.setTripType(tripType);
        trip.setTime(time);

        tripService.createTrip(trip);
        return "redirect:/my-trips";
    }
}  
