package com.instagram.service;


import com.instagram.dto.PostDTO;
import com.instagram.entity.Tags;
import com.instagram.exception.TrendingException;
import com.instagram.repository.TrendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class TrendingServiceImpl implements  TrendingService {

    @Autowired
    private TrendingRepository trendingRepository;

    @Override
    public Integer extractTags(Integer postId, PostDTO postDTO) throws TrendingException {

        String caption = postDTO.getCaption();
        Pattern pattern = Pattern.compile("\\#(\\w+)");
        Matcher matcher = pattern.matcher(caption);

        while ( matcher.find() ){
            String hashtag = matcher.group(1);
            if( trendingRepository.findByTagNameAndPostId(hashtag,postId).isPresent())
                continue;
            Tags tag = new Tags();
            tag.setPostId(postId);
            tag.setTagname(hashtag);
            trendingRepository.save(tag);
        }
        return 1;
    }

    @Override
    public List<Integer> findPostIdsByTagName(String tagname) throws TrendingException {
        return trendingRepository.findPostIdsByTagName(tagname);
    }

    @Override
    public Integer deleteTagsByPostId(Integer postId) throws TrendingException {

        List<Integer> listOfTidByPostId = trendingRepository.findAllTidByPostId(postId);
        listOfTidByPostId.forEach( tid -> {
            trendingRepository.deleteById(tid);
        });
        return listOfTidByPostId.size();
    }
}
