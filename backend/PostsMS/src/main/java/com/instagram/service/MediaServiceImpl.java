package com.instagram.service;


import com.instagram.dto.MediaDTO;
import com.instagram.entity.Media;
import com.instagram.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Override
    public Integer addMedia(Integer postid, List<Integer> imgIds) {
        for (int i = 0; i < imgIds.size(); i++) {
            Media m = new Media();
            m.setImgId( imgIds.get(i) );
            m.setPlace( i );
            m.setPostId( postid);
            mediaRepository.save(m);
        }
        return imgIds.size();
    }

    @Override
    public List<MediaDTO> getMediaOfPost(Integer postid) {
        List<Media> medias = mediaRepository.getAllMediaByPostId(postid);
        List<MediaDTO> mediaDTOS = medias.stream()
                .map(m -> {
                    return new MediaDTO(m);
                })
                .collect(Collectors.toList());
        return mediaDTOS;
    }

    @Override
    public Integer deleteMediaOfPost(Integer postid) {
        return mediaRepository.deleteAllByPostId(postid);
    }
}
