package com.ncit.teko.controller;

import com.ncit.teko.model.User;
import com.ncit.teko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String startPage() {
        return "index";
    }

    @PostMapping("/signup")
    public String saveUser(@ModelAttribute User user, Model model){
        System.out.println("hello");

        String username = user.getUsername();
        String email = user.getEmail();
        System.out.println(username);

        if(userService.doesUserAlreadyExists(username,email)){

            if(userService.isEmailTaken(email)){

                model.addAttribute("errorMessage", "Account of this email already exists!");
                return "index";
            }

            if(userService.isUserNameTaken(username)){
                model.addAttribute("errorMessage","Username already taken!");
                return "index";
            }

        }
        userService.userSignup(user);
        return "index";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code){
        if(userService.verify(code)){
            return "verification_success";
        }else {
            return "verification_failure";
        }
    }
}
