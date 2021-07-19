package com.ncit.teko.controller;

import com.ncit.teko.functionality.PatternMatcher;
import com.ncit.teko.model.User;
import com.ncit.teko.service.UserSignupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignupController {

    @Autowired
    private UserSignupService userSignupService;

    @PostMapping("/signup")
    public String saveUser(@ModelAttribute User user, @RequestParam("password2") String confirmPassword, RedirectAttributes redirectAttributes){
        
        String username = user.getUsername();
        String email = user.getEmail();
        String fName = user.getFirstname();
        String lName = user.getLastname();
        String phoneNumber = user.getPhoneNumber();
        String password = user.getPassword();
        String gender = user.getGender();
        System.out.println(gender);

        if(!PatternMatcher.checkNamePattern(fName)){
            redirectAttributes.addFlashAttribute("errorMessage", "First name is invalid");
            return "redirect:/";
        }

        if(!PatternMatcher.checkNamePattern(lName)){
            redirectAttributes.addFlashAttribute("errorMessage", "Last name is invalid");
            return "redirect:/";
        }

        if(!PatternMatcher.checkEmailPattern(email)){
            redirectAttributes.addFlashAttribute("errorMessage", "Email address is invalid");
            return "redirect:/";
        }

        if(!PatternMatcher.checkUsernamePattern(username)){
            redirectAttributes.addFlashAttribute("errorMessage", "Username is invalid");
            return "redirect:/";
        }

        if(!PatternMatcher.checkPhoneNumberPattern(phoneNumber)){
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid phone number");
            return "redirect:/";
        }

        if(!PatternMatcher.checkPasswordPattern(password)){
            redirectAttributes.addFlashAttribute("errorMessage", "Password invalid");
            return "redirect:/";
        }

        if(!password.equals(confirmPassword)){
            redirectAttributes.addFlashAttribute("errorMessage", "Passwords don't match");
            return "redirect:/";
        }

        if(gender==null){
            redirectAttributes.addFlashAttribute("errorMessage", "Select a gender");
            return "redirect:/";
        }

        if(userSignupService.doesUserAlreadyExists(username,email)){

            if(userSignupService.isEmailTaken(email)){

                redirectAttributes.addFlashAttribute("errorMessage", "Account of this email already exists!");
                return "redirect:/";
            }

            if(userSignupService.isUserNameTaken(username)){
                redirectAttributes.addFlashAttribute("errorMessage","Username already taken!");
                return "redirect:/";
            }

        }
        userSignupService.userSignup(user);
        redirectAttributes.addFlashAttribute("errorMessage","verification mail sent");
        return "redirect:/";
    }

    @GetMapping("/signup/verify")
    public String verifyUser(@Param("code") String code, RedirectAttributes redirectAttributes){

        if(userSignupService.verify(code)){

            redirectAttributes.addFlashAttribute("errorMessage", "Verification Successful");
            return "redirect:/";
        }else {
            redirectAttributes.addFlashAttribute("errorMessage", "Verification Failed");
            return "redirect:/";
        }
    }
}
