package com.ncit.teko.service;

import com.ncit.teko.model.User;
import com.ncit.teko.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    @Autowired
    private UserRepo userRepo;

    public User loginUser(String email,String password){
        return userRepo.checkUser(email,password);
    }

}
