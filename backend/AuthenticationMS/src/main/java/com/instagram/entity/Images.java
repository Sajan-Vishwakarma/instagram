package com.instagram.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "images")
public class Images {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer imgId;
    private String imgUrl;
}
