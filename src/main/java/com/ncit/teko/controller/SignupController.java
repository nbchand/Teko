package com.ncit.teko.controller;

import java.util.Map;

import com.ncit.teko.functionality.PatternMatcher;
import com.ncit.teko.model.User;
import com.ncit.teko.service.UserSignupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignupController {

    @Autowired
    private UserSignupService userSignupService;

    @PostMapping("/signup")
    public ResponseEntity<?> saveUser(@RequestBody Map<String,String> json){
        
        String username = json.get("username");
        String email = json.get("email");
        String fName = json.get("firstname");
        String lName = json.get("lastname");
        String phoneNumber = json.get("phoneNumber");
        String password = json.get("password");
        String gender = json.get("gender");
        String confirmPassword = json.get("confirmPassword");

        User user = new User();
        user.setEmail(email);
        user.setFirstname(fName);
        user.setGender(gender);
        user.setLastname(lName);
        user.setPassword(password);
        user.setUsername(username);
        user.setPhoneNumber(phoneNumber);


        if(username.equals("")||email.equals("")||fName.equals("")||lName.equals("")||phoneNumber.equals("")||
            password.equals("")||gender.equals("")||confirmPassword.equals("")){
            return new ResponseEntity<>("Please fill all the necessary input fields",HttpStatus.OK);
        }
        
        if(!PatternMatcher.checkNamePattern(fName)){
            return new ResponseEntity<>("First name is invalid",HttpStatus.OK);
        }

        if(!PatternMatcher.checkNamePattern(lName)){
            return new ResponseEntity<>("Last name is invalid",HttpStatus.OK);
        }

        if(!PatternMatcher.checkEmailPattern(email)){
            return new ResponseEntity<>("Email address is invalid",HttpStatus.OK);
        }

        if(!PatternMatcher.checkUsernamePattern(username)){
            return new ResponseEntity<>("Username is invalid",HttpStatus.OK);
        }

        if(!PatternMatcher.checkPhoneNumberPattern(phoneNumber)){
            return new ResponseEntity<>("Invalid phone number",HttpStatus.OK);
        }

        if(!PatternMatcher.checkPasswordPattern(password)){
            return new ResponseEntity<>("Password invalid",HttpStatus.OK);

        }

        if(!password.equals(confirmPassword)){
            return new ResponseEntity<>("Passwords don't match",HttpStatus.OK);
        }

        if(userSignupService.doesUserAlreadyExists(username,email)){

            if(userSignupService.isEmailTaken(email)){
                return new ResponseEntity<>("Account of this email already exists!",HttpStatus.OK);
            }

            if(userSignupService.isUserNameTaken(username)){
                return new ResponseEntity<>("Username already taken!",HttpStatus.OK);
            }

        }
        userSignupService.userSignup(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/signup/verify")
    public String verifyUser(@Param("code") String code, RedirectAttributes redirectAttributes){

        if(userSignupService.verify(code)){

            redirectAttributes.addFlashAttribute("msg", "Verification Successful");
            return "redirect:/";
        }else {
            redirectAttributes.addFlashAttribute("msg", "Verification Failed");
            return "redirect:/";
        }
    }
}
