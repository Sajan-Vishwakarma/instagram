package com.instagram.dto;


import com.instagram.entity.Comment;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CommentsDTO {

    private Integer cid;

    @NotNull(message = "{comment.postid.notpresent}")
    private Integer postId;
    @NotNull(message = "{comment.userid.notpresent}")
    private Integer userId;
    @NotNull(message = "{comment.commentmsg.notpresent}")
    private String comment;

    private Date createdAt;

    public CommentsDTO(Comment c){
        this.cid = c.getCid();
        this.comment = c.getComment();
        this.userId = c.getUserId();
        this.createdAt = c.getCreatedAt();
        this.postId = c.getPostId();
    }
}
