package com.instagram.controller;


import com.instagram.dto.ImageDTO;
import com.instagram.exception.ImageException;
import com.instagram.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("images")
@CrossOrigin(origins = "*")
public class ImagesController {

    @Autowired
    private ImagesService imagesService;

    @Autowired
    private Environment environment;

    @GetMapping("image/{id}")
    public ResponseEntity<ImageDTO> getImage(@PathVariable Integer id) throws ImageException {
        ImageDTO imgDto = imagesService.getImage(id);
        return new ResponseEntity<>(imgDto, HttpStatus.OK);
    }

    @PutMapping("image/{id}")
    public ResponseEntity<ImageDTO> updateImage(@PathVariable Integer id,
                                                @RequestParam(value = "image") MultipartFile file) throws IOException, ImageException{
        ImageDTO imgDto = imagesService.updateImage(id,file);
        return new ResponseEntity<>(imgDto, HttpStatus.OK);
    }

    @PostMapping("image")
    public ResponseEntity<Integer> uploadImage(@RequestParam(value = "image") MultipartFile file)
        throws IOException, ImageException {
        Integer imgId = imagesService.uploadImage(file);
        return new ResponseEntity<>(imgId, HttpStatus.CREATED);
    }

    @PostMapping("uploads")
    public ResponseEntity<List<Integer>> uploadMultipleImages(
            @RequestParam(value = "images") MultipartFile[] files) throws IOException, ImageException{

        List<Integer> imgIds = imagesService.uploadMulipleImages(files);
        return new ResponseEntity<>(imgIds, HttpStatus.CREATED);
    }
}
