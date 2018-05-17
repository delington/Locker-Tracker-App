package com.locker.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.locker.exception.LockerException;
import com.locker.form.UserRegisterForm;
import com.locker.model.Locker;
import com.locker.model.User;
import com.locker.model.UserDetailsImpl;
import com.locker.repository.LockerRepository;
import com.locker.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private static final String USER_ROLE = "USER";

    private UserRepository userRepo;

    private LockerRepository lockerRepo;

    private EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepo, LockerRepository lockerRepo, EmailService emailService) {
        this.userRepo = userRepo;
        this.lockerRepo = lockerRepo;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email.toLowerCase());

        if (user == null) {
            throw new UsernameNotFoundException("User not found by its email in the database!");
        }

        return new UserDetailsImpl(user);
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User getLoggedInUserByName(String email) {
        return userRepo.findByEmail(email.toLowerCase());
    }

    @Transactional
    public void signUpUser(UserRegisterForm userForm) throws LockerException, UnsupportedEncodingException {
        String userFormEmail = userForm.getEmail().toLowerCase();
        User user = userRepo.findByEmail(userFormEmail);

        if (user != null) {
            String errorMessage = "This user already exists!";
            log.error("LockerException thrown: " + errorMessage);
            throw new LockerException(errorMessage);
        }

        user = new User();
        user.getRoles().add(USER_ROLE);
        user.setEmail(userFormEmail);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userForm.getPassword());
        String activationCode = UUID.randomUUID().toString();

        user.setPassword(hashedPassword);
        user.setEnabled(false);
        user.setActivation(activationCode);
        userRepo.save(user);

        emailService.sendActivationLink(userFormEmail, activationCode);

        log.info(
            String.format("UserService: user saved in the database successfully. [user.email = %s]",
                    userFormEmail));
    }

    @Transactional
    public void activateUser(String code) throws LockerException {
        User user = userRepo.findByActivation(code);

        if (user == null) {
            log.info("User not found with this activation code");
            throw new LockerException("LockerException: User not found with this activation code");
        }

        user.setEnabled(true);
        user.setActivation("");

        userRepo.save(user);

        log.info("User activated its account.");
    }

    /**
     * Delete user without deleting the corresponding Locker objects
     * @param logged in User's email
     */
    @Transactional
    public void delete(String email) {
        User loggedInUser = userRepo.findByEmail(email);
        Assert.notNull(loggedInUser, "Logged in user not found by his/her email!");

        List<Locker> userLockers = lockerRepo.findByOwnerId(loggedInUser.getId());

        if (userLockers != null) {
            userLockers.forEach(locker -> {
                locker.setOwnerId(null);
            });
            lockerRepo.save(userLockers);
        }

        userRepo.delete(loggedInUser);
        log.info("User deleted its account.");
    }

}
