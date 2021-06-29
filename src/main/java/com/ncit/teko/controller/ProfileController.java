package com.ncit.teko.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String showProfile(){
        return "profile";
    }

    @GetMapping("/mytrips")
    public String showTrips(){
        return "mytrips";
    }
}
