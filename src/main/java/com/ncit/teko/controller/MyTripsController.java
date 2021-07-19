package com.ncit.teko.controller;

import javax.servlet.http.HttpSession;

import com.ncit.teko.model.User;
import com.ncit.teko.service.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyTripsController {
    @Autowired
    private UserProfileService userProfileService;
    @GetMapping("/my-trips")
    public String showTrips(HttpSession session, Model model){

        if(session.getAttribute("userId")==null){
            return "index";
        }
        int userId = (int)session.getAttribute("userId");
        User user = userProfileService.fetchUserByUserId(userId);
        model.addAttribute("username1", "@"+user.getUsername());
        return "mytrips";
    }
}
