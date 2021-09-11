package com.ncit.teko.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.ncit.teko.functionality.PatternMatcher;
import com.ncit.teko.model.User;
import com.ncit.teko.service.UserProfileService;
import com.ncit.teko.service.UserSignupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserSignupService userSignupService;

    @GetMapping("")
    public String showProfile(Model model, HttpSession session){

        if(session.getAttribute("userId")==null){
            return "index";
        }

        int userId = (int)session.getAttribute("userId");
        User user = userProfileService.fetchUserByUserId(userId);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }

    @PostMapping("/update-username")
    public ResponseEntity<?> updateUserName(@RequestBody String newUsername, HttpSession session){

        if(!PatternMatcher.checkUsernamePattern(newUsername)){
            return new ResponseEntity<>("invalid username",HttpStatus.OK);
        }

        if(userSignupService.isUserNameTaken(newUsername)){
            return new ResponseEntity<>("username is already taken",HttpStatus.OK);
        }
        
        int userId = (int)session.getAttribute("userId");
        userProfileService.updateUsername(userId, newUsername);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/update-email")
    public ResponseEntity<?> updateEmail(@RequestBody String newEmail, HttpSession session){

        if(!PatternMatcher.checkEmailPattern(newEmail)){
            return new ResponseEntity<>("invalid characters in the email",HttpStatus.OK);
        }
        if(userSignupService.isEmailTaken(newEmail)){
            return new ResponseEntity<>("account of this email already exists",HttpStatus.OK);            
        }

        int userId = (int)session.getAttribute("userId");
        User user = userProfileService.fetchUserByUserId(userId);
        
        userProfileService.sendConfirmMail(user, newEmail);

        return new ResponseEntity<>("success",HttpStatus.OK);
    }

    @GetMapping("/change-email")
    public String setNewEmail(){
        /* logic here */
        return "redirect:/";
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> json, HttpSession session){

        String prevPassword = json.get("prevPassword");
        String newPassword = json.get("newPassword");
        String confirmNewPassword = json.get("confirmNewPassword");

        int userId = (int)session.getAttribute("userId");
        User user = userProfileService.fetchUserByUserId(userId);

        if(!userProfileService.validatePassword(user, prevPassword)){
            return new ResponseEntity<>("old password is incorrect",HttpStatus.OK);
        }
        if(!newPassword.equals(confirmNewPassword)){
            return new ResponseEntity<>("new passwords don't match",HttpStatus.OK);             
        }
        if(!PatternMatcher.checkPasswordPattern(newPassword) || !PatternMatcher.checkPasswordPattern(confirmNewPassword)){
            return new ResponseEntity<>("new password is invalid",HttpStatus.OK);      
        }
        if(newPassword.equals(prevPassword)){
            return new ResponseEntity<>("new password can't be same as old password",HttpStatus.OK);
        }
        userProfileService.updatePassword(userId, newPassword);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
