package com.instagram.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentsInfo {
    private CommentsDTO commentsDTO;
    private UserInfoDTO userInfo;
}
