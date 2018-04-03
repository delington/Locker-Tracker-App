package com.locker.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.locker.exception.LockerException;
import com.locker.form.UserRegisterForm;
import com.locker.service.EmailService;
import com.locker.service.LockerService;
import com.locker.service.UserService;
import com.locker.validator.UserRegisterFormValidator;

@Controller
public class PublicController {

    private static final Logger log = LoggerFactory.getLogger(PublicController.class);

    private static final String USER_REGISTER_FORM_ID = "registerForm";

    private UserRegisterFormValidator formValidator;

    private LockerService lockerService;

    private UserService userService;

    private EmailService emailService;

    @Autowired
    public void setFormValidator(UserRegisterFormValidator formValidator) {
        this.formValidator = formValidator;
    }

    @Autowired
    public void setLockerService(LockerService lockerService) {
        this.lockerService = lockerService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(formValidator);
    }

    @RequestMapping("/")
    public String index() {
        log.info("Main url called. Returning login page...");
        return "redirect:lockers";
    }

    @RequestMapping("/login")
    public String login() {
        log.info("/login url called.");
        return "login";
    }

    @RequestMapping("/signup")
    public String signUp(Model model) throws LockerException {
        log.info("/signup url called.");
        model.addAttribute("registerForm", new UserRegisterForm());
        return "signup";
    }

    @RequestMapping("/signup/regUser")
    public String signUpUser(Model model,
            @Valid @ModelAttribute(USER_REGISTER_FORM_ID) UserRegisterForm userForm, BindingResult bindingResult)
                    throws LockerException {
        log.info("/signup/regUser url called.");

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        userService.signUpUser(userForm);
        emailService.sendRegistrationSuccessfullMessage(userForm.getEmail());

        model.addAttribute("regSuccess", true);
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
