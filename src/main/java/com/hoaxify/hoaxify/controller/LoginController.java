package com.hoaxify.hoaxify.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    public static final String API_1_0_LOGIN = "/api/1.0/login";

    @PostMapping(API_1_0_LOGIN)
    void handleLogin() {

    }

}
