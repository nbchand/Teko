package com.ncit.teko.controller;

import com.ncit.teko.model.User;
import com.ncit.teko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String userLogin(){
        return "index";
    }

    @PostMapping("/login")
    public String userLogin(@ModelAttribute User user, Model model, HttpSession session){
        User u = userService.loginUser(user.getEmail(), user.getPassword());

        if (u != null) {
            if(u.isEnabled()) {
                session.setAttribute("activeUser", u.getUsername());
                session.setMaxInactiveInterval(1000);
                return "redirect:/profile";
            }
            else {
                model.addAttribute("errorMessage","Verify email first");
                return "index";
            }
        }
        model.addAttribute("errorMessage","Acount doesnot exist");
        return "index";
    }

    @GetMapping("/logout")
    public String userLogout(HttpSession session){

        session.invalidate();
        return "index";
    }
}
