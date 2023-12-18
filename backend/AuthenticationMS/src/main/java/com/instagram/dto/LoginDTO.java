package com.instagram.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {

    @NotNull(message = "{login.username.notpresent}")
    private String username;

    @NotNull(message = "{login.password.notpresent}")
    private String password;
}
