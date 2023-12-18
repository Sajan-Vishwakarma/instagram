package com.instagram.service;

import com.instagram.dto.ImageDTO;
import com.instagram.exception.ImageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImagesService {

    public Integer uploadImage(MultipartFile file) throws IOException, ImageException;
    public List<Integer> uploadMulipleImages(MultipartFile[] files) throws IOException, ImageException;
    public ImageDTO getImage(Integer imgId) throws ImageException;
    public ImageDTO updateImage(Integer imgId, MultipartFile file) throws IOException, ImageException;
}
