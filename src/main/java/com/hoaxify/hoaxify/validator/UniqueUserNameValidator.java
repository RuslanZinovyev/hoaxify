package com.hoaxify.hoaxify.validator;

import com.hoaxify.hoaxify.annotation.UniqueUserName;
import com.hoaxify.hoaxify.repository.UserRepository;
import com.hoaxify.hoaxify.user.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        User inDB = userRepository.findByUsername(value);
        if (inDB == null) {
            return true;
        }
        return false;
    }
}
