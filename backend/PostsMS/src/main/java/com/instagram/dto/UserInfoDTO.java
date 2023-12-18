package com.instagram.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDTO {

    private Integer userId;
    private String username;
    private String profileImageURL;
}
