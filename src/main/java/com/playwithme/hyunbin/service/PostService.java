package com.playwithme.hyunbin.service;

import com.playwithme.hyunbin.domain.Post;
import com.playwithme.hyunbin.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 전체 목록 조회
    public Page<Post> getAllpostsByPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("boardId").descending());
        return postRepository.findAll(pageable);
    }

    // 검색 후 목록 조회

    @Transactional
    public Page<Post> search(String keyword, Pageable pageable) {
        Page<Post> searchList = postRepository.findByTitleContaining(keyword,pageable);
        return searchList;
    }


    // 특정 게시글 조회
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    // 게시글 작성, 수정
    @Transactional
    public Post save(Post post) {
        return postRepository.saveAndFlush(post);
    }

    // 게시글 삭제
    public void deletePost(Long id){
        postRepository.deleteById(id);
    }


}
