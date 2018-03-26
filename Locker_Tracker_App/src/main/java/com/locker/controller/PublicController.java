package com.locker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "redirect:lockers";
    }
    
    @RequestMapping("/login")
    public String login(Model model) {
        log.info("/login url called.");
        return "login";
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
    
    //TODO finish
    @RequestMapping(value = "/edit" , method = RequestMethod.POST)
    public String edit(Model model, @RequestParam(value = "lockerId") Integer lockerId) {
        log.info("/edit url called.");
        
//        lockerService.edit(lockerId);
//        model.addAttribute("lockers", lockerService.getLockers());
        
        return "lockers";
    }
}
