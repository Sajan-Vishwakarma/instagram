package com.instagram.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PostInfoDTO {
    private PostDTO postDTO;
    private UserInfoDTO userInfoDTO;
    private List<MediaInfo> mediaInfos;
}
