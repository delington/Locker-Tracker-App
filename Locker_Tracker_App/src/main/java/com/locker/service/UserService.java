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
import com.locker.model.Role;
import com.locker.model.User;
import com.locker.model.UserDetailsImpl;
import com.locker.repository.RoleRepository;
import com.locker.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private static final String USER_ROLE = "USER";

    private UserRepository userRepo;

    private RoleRepository roleRepo;

    private EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepo, RoleRepository roleRepo, EmailService emailService) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.emailService = emailService;
    }

    @Override
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
        checkRoleExistsAndSetToUser(user);

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

    private void checkRoleExistsAndSetToUser(User user) {

        Role userRole = roleRepo.findByRole(USER_ROLE);
        if (userRole != null) {
            user.getRoles().add(userRole);
        } else {
            Role newRole = new Role(USER_ROLE);
            roleRepo.save(newRole);
            user.addRole(newRole);
        }
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

        log.info("User activated its account.");
    }

    public void delete(String email) {
        User loggedInUser = userRepo.findByEmail(email);
        Assert.notNull(loggedInUser, "Logged in user not found by his email!");

        loggedInUser.getLockers().remove(0);
        loggedInUser.setRoles(null);
        userRepo.save(loggedInUser);
        userRepo.delete(loggedInUser);
        log.info("User deleted its account.");
    }

}
