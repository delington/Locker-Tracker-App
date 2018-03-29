package com.locker.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.locker.exception.LockerException;
import com.locker.form.UserRegisterForm;
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

    public void signUpUser(UserRegisterForm userForm) throws LockerException {
        String userFormEmail = userForm.getEmail();
        User user = userRepo.findOneByEmail(userFormEmail);

        if (user != null) {
            String errorMessage = "This user is already exists!";
            log.error("LockerException thrown: " + errorMessage);
            throw new LockerException(errorMessage);
        }

        user = new User();
        user.setEmail(userFormEmail);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userForm.getPassword());

        user.setPassword(hashedPassword);
        userRepo.save(user);

        log.info(
            String.format("UserService: user saved in the database successfully. [user.email=%s]"),
            userFormEmail);
    }

}
