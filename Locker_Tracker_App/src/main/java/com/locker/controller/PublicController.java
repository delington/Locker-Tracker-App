package com.locker.controller;

import java.security.Principal;

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
    public String listLockers(Model model, Principal principal) {
        
        log.info("/lockers url called.");
        model.addAttribute("lockers", lockerService.getLockers());
        model.addAttribute("userEmail", principal.getName());
        
        return "lockers";
    }
    
    @RequestMapping("/users")
    public String listUsers(Model model) {
        
        log.info("/users url called.");
        model.addAttribute("users", userService.getUsers());
        
        return "users";
    }
    
    @RequestMapping(value = "/edit" , method = RequestMethod.POST)
    public String editLockerOwner(Model model, Principal principal, 
            @RequestParam(value = "lockerId") Integer lockerId) {
        
        log.info(String.format("/edit url called. lockerId=[%s]", lockerId));
        lockerService.editOwner(principal.getName(), lockerId);
        log.info("/edit end point's tasks excecuted successfully.");
        
        return "redirect:lockers";
    }
    
    @RequestMapping(value = "/remove" , method = RequestMethod.DELETE)
    public String removeLockerOwner(Model model, Principal principal) {
        
        log.info(String.format("/remove url called."));
        lockerService.removeLocker(principal.getName());
        log.info("/remove end point's tasks excecuted successfully.");
        
        return "redirect:lockers";
    }
}
