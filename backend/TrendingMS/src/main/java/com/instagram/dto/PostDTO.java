package com.instagram.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PostDTO {

    private Integer postId;

    @NotNull(message = "{post.userid.notpresent}")
    private Integer userId;

    @NotNull(message = "{post.privacy.notpresent}")
    private Integer privacy;

    private String caption;
    private Date createdAt;

}
