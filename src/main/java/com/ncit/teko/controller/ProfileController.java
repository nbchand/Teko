package com.ncit.teko.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session){

        if(session.getAttribute("activeUser")==null){
            return "index";
        }

        model.addAttribute("username", (String)session.getAttribute("activeUser"));
        return "profile";
    }

    @GetMapping("/mytrips")
    public String showTrips(HttpSession session, Model model){

        if(session.getAttribute("activeUser")==null){
            return "index";
        }

        return "mytrips";
    }
}
