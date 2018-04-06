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

import com.locker.exception.LockerException;
import com.locker.service.LockerService;

@Controller
public class LockerController {

    private static final Logger log = LoggerFactory.getLogger(LockerController.class);

    private LockerService lockerService;

    @Autowired
    public void setLockerService(LockerService lockerService) {
        this.lockerService = lockerService;
    }

    @RequestMapping("/lockers")
    public String listLockers(Model model, Principal principal) {

        log.info("/lockers url called.");
        model.addAttribute("lockers", lockerService.getLockers());
        model.addAttribute("userEmail", principal.getName());

        return "lockers";
    }

    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public String addLockerOwner(Model model, Principal principal,
        @RequestParam(value = "lockerId") Integer lockerId) throws LockerException {

        log.info(String.format("/add url called. lockerId=[%s]", lockerId));
        lockerService.addOwner(principal.getName(), lockerId);
        log.info("/add end point's tasks excecuted successfully.");

        return "redirect:lockers";
    }

    @RequestMapping(value = "/remove" , method = RequestMethod.DELETE)
    public String removeLockerOwner(Model model, Principal principal,
        @RequestParam(value = "lockerId") Integer lockerId) throws LockerException {

        log.info("/remove url called with lockerId=[{}]", lockerId);
        lockerService.removeLocker(lockerId, principal.getName());
        log.info("/remove end point's tasks excecuted successfully.");

        return "redirect:lockers";
    }

}
