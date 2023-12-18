package com.instagram.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tags")
public class Tags {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer tid;
    private String tagname;
    private Integer postId;
}