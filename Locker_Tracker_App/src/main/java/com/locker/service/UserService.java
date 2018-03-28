package com.locker.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.locker.exception.LockerException;
import com.locker.model.User;
import com.locker.repository.UserRepository;

@Service
public class UserService {
    
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    
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

    public void signUpUser(String email, String password) throws LockerException {
        User user = userRepo.findOneByEmail(email);
        
        if (user != null) {
            String errorMessage = "This user is already exists!";
            log.error("LockerException thrown: " + errorMessage);
            throw new LockerException(errorMessage);
        }
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        
        user = new User(email, hashedPassword);
        userRepo.save(user);
        
        log.info("UserService: user saved in the database successfully.");
    }

}
