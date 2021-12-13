package com.hoaxify.hoaxify.user;

import com.hoaxify.hoaxify.annotation.UniqueUserName;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "{hoaxify.constraints.userName.NotNull.message}")
    @Size(min = 4, max = 255)
    @UniqueUserName
    private String userName;

    @NotNull(message = "{hoaxify.constraints.displayName.NotNull.message}")
    @Size(min = 4, max = 255)
    private String displayName;

    @NotNull(message = "{hoaxify.constraints.password.NotNull.message}")
    @Size(min = 8, max = 255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{hoaxify.constraints.password.Pattern.message}")
    private String password;

}
