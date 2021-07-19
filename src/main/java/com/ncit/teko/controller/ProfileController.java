package com.ncit.teko.controller;

import javax.servlet.http.HttpSession;

import com.ncit.teko.functionality.PatternMatcher;
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
        model.addAttribute("username1", "@"+user.getUsername());
        model.addAttribute("username2", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }

    @PostMapping("/profile/update-username")
    public String updateUserName(@RequestParam("username") String newUsername, RedirectAttributes redirectAttributes, HttpSession session){

        if(!PatternMatcher.checkUsernamePattern(newUsername)){
            redirectAttributes.addFlashAttribute("updateMessage", "invalid username");
            return "redirect:/profile";
        }

        if(userSignupService.isUserNameTaken(newUsername)){

            redirectAttributes.addFlashAttribute("updateMessage", "username is already taken");
            return "redirect:/profile";
        }
        
        int userId = (int)session.getAttribute("userId");
        userProfileService.updateUsername(userId, newUsername);

        redirectAttributes.addFlashAttribute("updateMessage", "username updated successfully");
        return "redirect:/profile";
    }

    @PostMapping("/profile/update-email")
    public String updateEmail(@RequestParam("email") String newEmail, RedirectAttributes redirectAttributes, HttpSession session){
        if(!PatternMatcher.checkEmailPattern(newEmail)){
            redirectAttributes.addFlashAttribute("updateMessage", "invalid characters in the email");
            return "redirect:/profile";
        }
        if(userSignupService.isEmailTaken(newEmail)){
            redirectAttributes.addFlashAttribute("updateMessage", "account of this email already exists");
            return "redirect:/profile";            
        }

        int userId = (int)session.getAttribute("userId");
        User user = userProfileService.fetchUserByUserId(userId);
        
        userProfileService.sendConfirmMail(user, newEmail);
        redirectAttributes.addFlashAttribute("updateMessage", "confirmation email sent");
        return "redirect:/profile";
    }

    @GetMapping("/profile/change-email")
    public String setNewEmail(){
        /* logic here */
        return "redirect:/";
    }

}
