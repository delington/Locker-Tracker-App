package com.locker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locker.model.User;
import com.locker.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepo;

    @Autowired
    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
    
    public List<User> getUsers() {
        return userRepo.findAll();
    }
    
    public User getLoggedInUserByName(String email) {
        return userRepo.findOneByEmail(email);
    } 

}
