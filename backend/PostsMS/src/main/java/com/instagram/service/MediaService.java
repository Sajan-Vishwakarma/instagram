package com.instagram.service;

import com.instagram.dto.MediaDTO;

import java.util.List;

public interface MediaService {

    public Integer addMedia(Integer postid, List<Integer> imgIds);
    public List<MediaDTO> getMediaOfPost(Integer postid);
    public Integer deleteMediaOfPost(Integer postid);
}
