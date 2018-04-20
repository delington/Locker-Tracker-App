package com.locker.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.locker.exception.LockerException;
import com.locker.form.UserRegisterForm;
import com.locker.model.LoginNotification;
import com.locker.service.EmailService;
import com.locker.service.UserService;
import com.locker.validator.UserRegisterFormValidator;

@Controller
public class PublicController {

    private static final Logger log = LoggerFactory.getLogger(PublicController.class);

    private static final String USER_REGISTER_FORM_ID = "registerForm";

    private UserRegisterFormValidator formValidator;

    private UserService userService;

    private EmailService emailService;

    @Autowired
    public void setFormValidator(UserRegisterFormValidator formValidator) {
        this.formValidator = formValidator;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @InitBinder("registerForm")
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
        @Valid @ModelAttribute(USER_REGISTER_FORM_ID) UserRegisterForm userForm,
        BindingResult bindingResult)
                    throws LockerException, UnsupportedEncodingException {
        log.info("/signup/regUser url called.");

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        userService.signUpUser(userForm);
        emailService.sendRegistrationSuccessfullMessage(userForm.getEmail());

        LoginNotification notification =
                new LoginNotification("login.activation", "alert alert-success");

        model.addAttribute("notification", notification);
        log.info(notification.toString());
        return "login";
    }

    @RequestMapping("/users")
    public String listUsers(Model model) {

        log.info("/users url called.");
        model.addAttribute("users", userService.getUsers());

        return "users";
    }

    @RequestMapping(value = "/activation/{code}", method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("code") String code) {
        log.info("/activation url called.");

        LoginNotification notification = new LoginNotification();

        try {
            userService.activateUser(code);
            notification.setMessage("activation.success");
            notification.setType("alert alert-success");
        } catch (LockerException ex) {
            notification.setMessage("activation.user-not-found");
            notification.setType("alert alert-danger");
        }

        model.addAttribute("notification", notification);
        return "login";

    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String delete(Model model, Principal principal) {
        String loggedInUserEmail = principal.getName();
        log.info("/delete url called. Logged in User email = [{}]", loggedInUserEmail);

        userService.delete(loggedInUserEmail);
        SecurityContextHolder.clearContext();

        model.addAttribute("notification", new LoginNotification("login.user-deleted", "alert alert-success"));
        return "login";
    }
}
