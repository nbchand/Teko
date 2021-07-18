package com.ncit.teko.service;

import com.ncit.teko.model.User;
import com.ncit.teko.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserProfileService {

    @Autowired
    private UserRepo userRepo;

    public User fetchUserByUserId(int userId){

        return userRepo.findUserByUserId(userId);
    }

    public void updateUsername(int userId, String newUsername){
        userRepo.updateUsername(userId, newUsername);
    }
    
}
