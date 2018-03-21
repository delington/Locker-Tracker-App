package com.locker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.locker.form.LoginForm;
import com.locker.service.PublicService;

@Controller
public class PublicController {

private static final Logger log = LoggerFactory.getLogger(PublicController.class);
    
    private PublicService publicService;
    
    @Autowired
    public void setPublicService(PublicService publicService) {
        this.publicService = publicService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        log.info("Main url called. Returning index page...");
        model.addAttribute("loginForm", new LoginForm());
        
        return "index";
    }
}
