package com.instagram.dto;

import com.instagram.entity.Media;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MediaDTO {

    @NotNull(message = "{media.imgid.notpresent}")
    private Integer imgId;
    @NotNull(message = "{media.place.notpresent}")
    private Integer place;

    public MediaDTO(Media m){
        this.imgId = m.getImgId();
        this.place = m.getPlace();
    }

}
