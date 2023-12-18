package com.instagram.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "follows")
public class Follow {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer fid;

    private Integer followeeId;
    private Integer followerId;
}
