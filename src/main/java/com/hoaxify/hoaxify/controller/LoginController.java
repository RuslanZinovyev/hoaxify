package com.hoaxify.hoaxify.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.hoaxify.shared.CurrentUser;
import com.hoaxify.hoaxify.shared.Views;
import com.hoaxify.hoaxify.user.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    public static final String API_1_0_LOGIN = "/api/1.0/login";

    @PostMapping(API_1_0_LOGIN)
    @JsonView(Views.Base.class)
    User handleLogin(@CurrentUser User loggedInUser) {
        return loggedInUser;
    }

}
