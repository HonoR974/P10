package com.clientui.dto;



import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;

      private String username;


    private String password;

    private String matchingPassword;

    private String email;
    private boolean enabled;
    private boolean connected;

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", matchingPassword='" + matchingPassword + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", connected=" + connected +
                '}';
    }
}
