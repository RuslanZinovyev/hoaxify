package com.hoaxify.hoaxify;

import com.hoaxify.hoaxify.user.User;

public class TestUtil {

    public static User createValidUser() {
        User user = new User();
        user.setUsername("test-user");
        user.setDisplayName("test-display");
        user.setPassword("P4ssword");
        user.setImage("profile-image.png");
        return user;
    }

    public static User createValidUser(String userName) {
        User user = createValidUser();
        user.setUsername(userName);
        return user;
    }
}
