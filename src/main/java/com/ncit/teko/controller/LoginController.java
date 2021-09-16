package com.ncit.teko.controller;

import com.ncit.teko.model.User;
import com.ncit.teko.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody Map<String,String> json, HttpSession session){

        User u = userLoginService.loginUser(json.get("email"));

        if (u != null) {
            if(!u.isEnabled()){
                return new ResponseEntity<>("Verify email first",HttpStatus.OK);
            }
            if(!json.get("password").equals(u.getPassword())){
                return new ResponseEntity<>("Invalid password for the provided email",HttpStatus.OK);
            }
            session.setAttribute("userId", u.getUserId());
            session.setMaxInactiveInterval(1000);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Account doesnot exist",HttpStatus.OK);
    }
}
