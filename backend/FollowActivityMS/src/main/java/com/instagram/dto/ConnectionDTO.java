package com.instagram.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConnectionDTO {

    @NotNull(message = "{follow.followee.notpresent}")
    private String followeeName;

    @NotNull(message = "{follow.follower.notpresent}")
    private String followerName;
}