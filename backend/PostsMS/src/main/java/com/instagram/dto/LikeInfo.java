package com.instagram.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikeInfo {
    private Integer postId;
    private Integer userId;
    private Boolean didLiked;
}
