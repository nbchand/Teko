package com.ncit.teko.controller;

import com.ncit.teko.model.User;
import com.ncit.teko.service.UserLoginService;
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
    private UserLoginService userLoginService;

    @GetMapping("/login")
    public String userLogin(){
        return "index";
    }

    @PostMapping("/login")
    public String userLogin(@ModelAttribute User user, Model model, HttpSession session){
        User u = userLoginService.loginUser(user.getEmail(), user.getPassword());

        if (u != null) {
            if(u.isEnabled()) {
                session.setAttribute("userId", u.getId());
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
