package com.hoaxify.hoaxify;

import com.hoaxify.hoaxify.user.User;

public class TestUtil {

    public static User createValidUser() {
        User user = new User();
        user.setUserName("test-user");
        user.setDisplayName("test-display");
        user.setPassword("P4ssword");
        user.setImage("profile-image.png");
        return user;
    }
}
