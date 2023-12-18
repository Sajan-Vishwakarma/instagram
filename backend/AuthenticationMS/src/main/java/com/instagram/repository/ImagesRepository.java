package com.instagram.repository;

import com.instagram.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images,Integer> {
}
