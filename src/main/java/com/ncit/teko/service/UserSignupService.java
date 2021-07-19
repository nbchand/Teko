package com.ncit.teko.service;

import com.ncit.teko.functionality.EmailSender;
import com.ncit.teko.functionality.StringHandler;
import com.ncit.teko.model.User;
import com.ncit.teko.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bytebuddy.utility.RandomString;

@Service
public class UserSignupService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailSender emailSender;

    public void userSignup(User user){
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
        user.setFirstname(StringHandler.converToTitleCase(user.getFirstname()));
        user.setLastname(StringHandler.converToTitleCase(user.getLastname()));
        userRepo.save(user);

        try {
            emailSender.sendVerificationEmail(user);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean isUserNameTaken(String username){
        return userRepo.existsUserByUsername(username);
    }

    public boolean isEmailTaken(String email){
        return userRepo.existsUserByEmail(email);
    }

    public boolean doesUserAlreadyExists(String username,String email){
        return userRepo.existsUserByUsernameOrEmail(username,email);
    }


    public boolean verify(String code){
        User user = userRepo.checkByVerificationCode(code);
        if(user == null || user.isEnabled()){
            return false;
        }
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepo.save(user);
            return true;
    }
}
