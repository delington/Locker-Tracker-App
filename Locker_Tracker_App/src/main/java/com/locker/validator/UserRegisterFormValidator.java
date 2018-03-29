package com.locker.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.locker.form.UserRegisterForm;

@Component
public class UserRegisterFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegisterForm form = (UserRegisterForm) target;

        if (!form.getPassword().equals(form.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm", "error.password.mismatch", "-The given passwords not match");
        }
    }

}
