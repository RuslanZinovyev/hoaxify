package com.hoaxify.hoaxify.controller;

import com.hoaxify.hoaxify.service.UserService;
import com.hoaxify.hoaxify.shared.GenericResponse;
import com.hoaxify.hoaxify.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    public static final String API_1_0_USERS = "/api/1.0/users";

    @Autowired
    UserService userService;

    @PostMapping(API_1_0_USERS)
    public GenericResponse createUser(@Valid @RequestBody User user) {
        userService.save(user);
        return new GenericResponse("User has been created");
    }
}
