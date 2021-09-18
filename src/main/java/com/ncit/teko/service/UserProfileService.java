package com.ncit.teko.service;

import com.ncit.teko.functionality.EmailSender;
import com.ncit.teko.model.User;
import com.ncit.teko.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
@Transactional
public class UserProfileService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailSender emailSender;

    public User fetchUserByUserId(int userId){
        return userRepo.findUserByUserId(userId);
    }

    public void updateUsername(int userId, String newUsername){
        userRepo.updateUsername(userId, newUsername);
    }

    public void updateEmail(int userId, String newEmail){
        userRepo.updateEmail(userId, newEmail);
    }

    public void sendConfirmMail(User user, String email){
        try{
            emailSender.changeEmailAddress(user, email);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public boolean validatePassword(User user,String password){
        if(user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
            return true;
        }
        return false;
    }

    public void updatePassword(int userId, String password){
        userRepo.updatePassword(userId, DigestUtils.md5DigestAsHex(password.getBytes()));
    }
    
}
