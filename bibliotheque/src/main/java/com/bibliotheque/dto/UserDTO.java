package com.bibliotheque.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;
    private String matchingPassword;

    @NotNull
    private String email;

    private boolean enabled;

    private boolean connected;
}
