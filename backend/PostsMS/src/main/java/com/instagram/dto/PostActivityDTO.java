package com.instagram.dto;

import com.instagram.entity.PostActivity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostActivityDTO {
    private Integer postId;
    private Integer likeCount;
    private Integer commentCount;

    public PostActivityDTO(PostActivity postActivity){
        this.postId = postActivity.getPostId();
        this.likeCount = postActivity.getLikeCount();
        this.commentCount = postActivity.getCommentCount();
    }
}

