package com.locker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.locker.form.LoginForm;
import com.locker.service.LockerService;
import com.locker.service.UserService;

@Controller
public class PublicController {

private static final Logger log = LoggerFactory.getLogger(PublicController.class);
    
    private LockerService lockerService;
    
    private UserService userService;
    
    @Autowired
    public void setLockerService(LockerService lockerService) {
        this.lockerService = lockerService;
    }
    
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        log.info("Main url called. Returning login page...");
        model.addAttribute("lockers", lockerService.getLockers());
        
        return "lockers";
    }
    
    @RequestMapping("/login")
    public String login(Model model) {
        log.info("Main url called. Returning index page...");
        model.addAttribute("loginForm", new LoginForm());
        
        return "index";
    }
    
    @RequestMapping("/lockers")
    public String lockers(Model model) {
        log.info("/lockers url called.");
        model.addAttribute("lockers", lockerService.getLockers());
        
        return "lockers";
    }
    
    @RequestMapping("/users")
    public String users(Model model) {
        log.info("/users url called.");
        model.addAttribute("users", userService.getUsers());
        
        return "users";
    }
}
