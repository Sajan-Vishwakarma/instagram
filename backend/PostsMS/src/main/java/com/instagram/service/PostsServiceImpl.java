package com.instagram.service;

import com.instagram.dto.PostDTO;
import com.instagram.entity.Post;
import com.instagram.exception.PostsException;
import com.instagram.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostsServiceImpl implements PostsService {

    @Autowired
    private PostsRepository postsRepository;

    @Override
    public Integer createPost(PostDTO postDTO) {
        Post post = new Post();
        post.setCaption(postDTO.getCaption());
        post.setUserId(postDTO.getUserId());
        post.setPrivacy(postDTO.getPrivacy());
        return postsRepository.save(post).getPostId();
    }

    @Override
    public PostDTO getPostDetails(Integer postId) throws PostsException {
        Optional<Post> op = postsRepository.findByPostId(postId);
        Post post = op.orElseThrow( ()-> new PostsException("POST.NOT_FOUND"));
        return new PostDTO(post);
    }

    @Override
    public List<PostDTO> getPublicPostIdsMadeByUser(Integer userId) {
        List<Post> allPosts = postsRepository.findAllPublicPostOfUser(userId);
        List<PostDTO> allPostsDto = allPosts.stream()
                .map( post -> {
                    return new PostDTO(post);
                })
                .collect(Collectors.toList());
        return allPostsDto;
    }

    @Override
    public List<PostDTO> getPostIdsMadeByUser(Integer userId) throws PostsException {
        List<Post> allPosts =  postsRepository.findAllByUserId(userId);
        List<PostDTO> allPostsDto = allPosts.stream()
                .map( post -> {
                    return new PostDTO(post);
                })
                .collect(Collectors.toList());
        return allPostsDto;
    }

    @Override
    public List<PostDTO> getPublicPostIds() {

        List<Post> allPosts =  postsRepository.findAllByPrivacy(0);
        List<PostDTO> allPostsDto = allPosts.stream()
                .map( post -> {
                    return new PostDTO(post);
                })
                .collect(Collectors.toList());
        return allPostsDto;
    }

    @Override
    public Integer deletePost(Integer postId) {
        return postsRepository.deleteAllByPostId(postId);
    }
}
