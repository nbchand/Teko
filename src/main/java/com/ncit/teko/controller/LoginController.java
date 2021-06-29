package com.ncit.teko.controller;

import com.ncit.teko.model.User;
import com.ncit.teko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String userLogin(@ModelAttribute User user, Model model){

        User u = userService.loginUser(user.getEmail(), user.getPassword());

        if (u != null) {
            if(u.isEnabled()) {
                return "profile";
            }
            else {
                model.addAttribute("errorMessage","Verify email first");
                return "index";
            }
        }
        model.addAttribute("errorMessage","Acount doesnot exist");
        return "index";
    }
}
