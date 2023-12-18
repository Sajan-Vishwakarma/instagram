package com.instagram.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowDTO {
    private Integer followerId;
    private Integer followeeId;
    private Boolean isConnected;
}