package com.instagram.dto;

import com.instagram.entity.User;
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
    @Pattern(regexp = "(([a-z0-9]+)[@]([a-z0-9]+)[.](com|in|org))", message = "{user.emailid.invalid}")
    private String emailId;

    @NotNull(message = "{user.fullname.notpresent}")
    @Pattern(regexp = "([A-Z][a-z]*)( [A-Z][a-z]*)*", message = "{user.fullname.invalid}")
    private String fullName;

    private String bio;

    private String profileImgId;
    private Date createdAt;

    public UserDTO(User user){
        this.userId = (user.getUserId());
        this.username = (user.getUsername());
        this.password = (user.getPassword());
        this.bio = (user.getBio());
        this.emailId = (user.getEmailId());
        this.createdAt = (user.getCreatedAt());
        this.fullName = (user.getFullName());
        this.profileImgId = (user.getProfileImgId());
    }
}
