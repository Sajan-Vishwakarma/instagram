package com.instagram.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "post_activity")
public class PostActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postActivityId;

    private Integer postId;
    private Integer likeCount;
    private Integer commentCount;
}
