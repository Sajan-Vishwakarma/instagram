package com.instagram.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikesDTO {

    @NotNull(message = "{like.postid.notpresent}")
    private Integer postId;

    @NotNull(message = "{like.userid.notpresent}")
    private Integer userId;
}

