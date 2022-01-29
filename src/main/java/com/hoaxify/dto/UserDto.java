package com.hoaxify.dto;

import com.hoaxify.hoaxify.user.User;
import lombok.Data;

@Data
public class UserDto {

    private long id;

    private String userName;

    private String displayName;

    private String image;

    public UserDto(User user) {
        this.setId(user.getId());
        this.setUserName(user.getUserName());
        this.setDisplayName(user.getDisplayName());
        this.setImage(user.getImage());
    }
}
