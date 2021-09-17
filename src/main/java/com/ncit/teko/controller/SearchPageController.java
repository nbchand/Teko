package com.ncit.teko.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchPageController {
    @GetMapping("/search")
    public String displaySearchPage(Model model, HttpSession session){

        if(session.getAttribute("userId")==null){
            return "redirect:/";
        }
        model.addAttribute("username", (String)session.getAttribute("username"));
        return "search";
    }
}
