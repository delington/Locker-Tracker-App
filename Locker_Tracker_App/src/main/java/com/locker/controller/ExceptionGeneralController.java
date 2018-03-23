package com.locker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionGeneralController {
    
    private static final Logger log = LoggerFactory.getLogger(ExceptionGeneralController.class);
    
    @ExceptionHandler
    public String exception(Exception ex, Model model) {
        model.addAttribute("exception", ex);
        
        log.error(ex.toString(), ex);
        return "error";
    }
    
}
