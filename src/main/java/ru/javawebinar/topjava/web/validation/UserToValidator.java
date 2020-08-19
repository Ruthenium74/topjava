package ru.javawebinar.topjava.web.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

@Component
public class UserToValidator implements Validator {

    @Autowired
    private UserService service;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserTo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserTo userTo = (UserTo) o;
        if (!service.isEmailFree(userTo)) {
            errors.rejectValue("email", "validation.user.mail.isNotFree", "User with this email already exists");
        }
    }
}
