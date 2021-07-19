package com.ncit.teko.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {
    @GetMapping("/logout")
    public String userLogout(HttpSession session){

        session.invalidate();
        return "redirect:/";
    }
}
