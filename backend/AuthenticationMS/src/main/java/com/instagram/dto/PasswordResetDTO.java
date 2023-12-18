package com.instagram.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordResetDTO {
    @NotNull(message = "{login.username.notpresent}")
    private String username;

    @NotNull(message = "{user.emailid.notpresent}")
    private String emailId;

    @NotNull(message = "{login.password.notpresent}")
    private String password;
}