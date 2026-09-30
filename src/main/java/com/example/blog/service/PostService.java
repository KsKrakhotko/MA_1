package com.example.blog.service;

import com.example.blog.dto.PostCreateDTO;
import com.example.blog.dto.PostDTO;
import com.example.blog.dto.PostUpdateDTO;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.mapper.PostMapper;
import com.example.blog.model.Post;
import com.example.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;
    private final PostMapper postMapper;

    @Transactional(readOnly = true)
    public List<PostDTO> index() {
        return repository.findAll().stream()
                .map(postMapper::map)
                .toList();
    }

    @Transactional
    public PostDTO create(PostCreateDTO postData) {
        Post post = postMapper.map(postData);
        repository.save(post);
        return postMapper.map(post);
    }

    @Transactional(readOnly = true)
    public PostDTO show(Long id) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        return postMapper.map(post);
    }

    @Transactional
    public PostDTO update(PostUpdateDTO postData, Long id) {
        Post post = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        postMapper.update(postData, post);
        repository.save(post);
        return postMapper.map(post);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}