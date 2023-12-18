package com.instagram.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserDTO {

    private Integer userId;

    @NotNull(message = "{user.username.notpresent}")
    @Pattern(regexp = "[a-z0-9@]+")
    private String username;

    @NotNull(message = "{user.password.notpresent}")
    private String password;

    @NotNull(message = "{user.emailid.notpresent}")
    @Pattern(regexp = "(([a-z]+)[@]([a-z]+)[.](com|in|org))", message = "{user.emailid.invalid}")
    private String emailId;

    @NotNull(message = "{user.fullname.notpresent}")
    @Pattern(regexp = "([A-Z][a-z]*)( [A-Z][a-z]*)*", message = "{user.fullname.invalid}")
    private String fullName;

    private String bio;

    private String profileImgId;
    private Date createdAt;
}