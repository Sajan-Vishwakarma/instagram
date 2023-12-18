package com.instagram.service;


import com.instagram.dto.ImageDTO;
import com.instagram.entity.Images;
import com.instagram.exception.ImageException;
import com.instagram.repository.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private Environment environment;

    @Override
    public Integer uploadImage(MultipartFile file) throws IOException, ImageException {
        String orgName = file.getOriginalFilename();
        Long timestamp = System.currentTimeMillis() / 1000;
        String fileName = timestamp + "_" + orgName;
        String filePath = environment.getProperty("IMAGE.SAVE_DESTINATION") + fileName;
        File dest = new File(filePath);

        try {
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            throw new ImageException("IMAGE.COULD_NOT_SAVE");
        }

        Images images = new Images();
        images.setImgUrl( environment.getProperty("IMAGE.FILE_PREFIX") + fileName);
        return imagesRepository.save(images).getImgId();
    }

    @Override
    public List<Integer> uploadMulipleImages(MultipartFile[] files) throws IOException, ImageException {
        List<Integer> imgIds = new ArrayList<>();
        for(MultipartFile file:files){
            Integer imgId = uploadImage(file);
            imgIds.add(imgId);
        }
        return imgIds;
    }

    @Override
    public ImageDTO getImage(Integer imgId) throws ImageException {
        Optional<Images> op = imagesRepository.findById(imgId);
        Images img = op.orElseThrow(() -> new ImageException("IMAGE.NOT_FOUND"));

        ImageDTO imgDto = new ImageDTO();
        imgDto.setImg_url(img.getImgUrl());
        return imgDto;
    }

    @Override
    public ImageDTO updateImage(Integer imgId, MultipartFile file) throws IOException, ImageException {

        Optional<Images> op = imagesRepository.findById(imgId);
        Images img = op.orElseThrow(() -> new ImageException("IMAGE.NOT_FOUND"));

        String orgName = file.getOriginalFilename();
        Long timestamp = System.currentTimeMillis() / 1000;
        String fileName = timestamp + "_" + orgName;
        String filePath = environment.getProperty("IMAGE.SAVE_DESTINATION") + fileName;
        File dest = new File(filePath);

        try {
            file.transferTo(dest);
            img.setImgUrl( environment.getProperty("IMAGE.FILE_PREFIX") + fileName);
        } catch (IllegalStateException e) {
            throw new ImageException("IMAGE.COULD_NOT_SAVE");
        }

        ImageDTO imgDto = new ImageDTO();
        imgDto.setImg_url(img.getImgUrl());
        return imgDto;
    }
}