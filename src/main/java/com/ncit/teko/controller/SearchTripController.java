package com.ncit.teko.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.ncit.teko.model.Trip;
import com.ncit.teko.model.User;
import com.ncit.teko.service.TripSearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SearchTripController {
    @Autowired
    TripSearchService tripSearchService;
    
    @PostMapping("/search-trips")
    public String findTrips(@RequestParam("departure") String departure, @RequestParam("destination") String destination,
                            HttpSession session, RedirectAttributes redirectAttributes){

        List<Trip> trips = tripSearchService.searchTrips(departure, destination);
        List<User> users = tripSearchService.getUsersByTrips(trips);

        redirectAttributes.addFlashAttribute("trips",trips);
        redirectAttributes.addFlashAttribute("departure",departure);
        redirectAttributes.addFlashAttribute("destination",destination);

        if(session.getAttribute("userId")==null){
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("users",users);
        return "redirect:/search";
    }
}
