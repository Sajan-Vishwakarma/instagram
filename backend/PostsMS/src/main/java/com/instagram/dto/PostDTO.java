package com.instagram.dto;

import com.instagram.entity.Post;
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

    public PostDTO(Post post){
        this.postId = post.getPostId();
        this.userId = post.getUserId();
        this.privacy = post.getPrivacy();
        this.caption = post.getCaption();
        this.createdAt = post.getCreatedAt();
    }
}
