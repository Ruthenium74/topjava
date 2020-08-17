package ru.javawebinar.topjava.web.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService service;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (!user.isNew() && service.get(user.getId()).getEmail().equals(user.getEmail())) {
            return;
        }
        if (!service.isEmailFree(user.getEmail())) {
            errors.rejectValue("email", "validation.user.mail.isNotFree", "User with this email already exists");
        }
    }
}
