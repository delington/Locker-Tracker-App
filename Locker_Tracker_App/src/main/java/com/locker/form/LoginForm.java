package com.locker.form;

import javax.validation.constraints.Pattern;

public class LoginForm {

    @Pattern(regexp = "[a-zA-Z]+")
    private String email;

    @Pattern(regexp = "[a-zA-Z]{8}")
    private String password;

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

    @Override
    public String toString() {
        return "LoginForm [email=" + email + ", password=" + password + "]";
    }

}
