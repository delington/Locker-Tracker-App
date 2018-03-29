package com.locker.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class UserRegisterForm {

    @Email(message = "Email address must be valid")
    private String email;

    @Size(min = 8, max = 30, message = "-Password length must be in 8 and 30 characters")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "-Can only contains numbers and letters")
    private String password;

    private String passwordConfirm;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public String toString() {
        return "UserRegisterForm [email=" + email + "]";
    }

}
