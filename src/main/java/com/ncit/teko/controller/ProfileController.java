package com.ncit.teko.controller;

import javax.servlet.http.HttpSession;

import com.ncit.teko.model.User;
import com.ncit.teko.service.UserProfileService;
import com.ncit.teko.service.UserSignupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserSignupService userSignupService;

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session){

        int userId = (int)session.getAttribute("userId");

        if(session.getAttribute("userId")==null){
            return "index";
        }
        User user = userProfileService.fetchUserByUserId(userId);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }

    @GetMapping("/mytrips")
    public String showTrips(HttpSession session, Model model){

        if(session.getAttribute("userId")==null){
            return "index";
        }

        return "mytrips";
    }

    @PostMapping("/usernameUpdate")
    private String updateUserName(@RequestParam("username") String newUsername, RedirectAttributes redirectAttributes, HttpSession session){

        if(userSignupService.isUserNameTaken(newUsername)){

            redirectAttributes.addFlashAttribute("updateMessage", "username is already taken");
            return "redirect:/profile";
        }
        
        int userId = (int)session.getAttribute("userId");
        userProfileService.updateUsername(userId, newUsername);

        redirectAttributes.addFlashAttribute("updateMessage", "username updated successfully");
        return "redirect:/profile";
    }

}
