package com.playwithme.hyunbin.repository;

import com.playwithme.hyunbin.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 게시판 목록 가져오기(내림차순)
    List<Post> findAll(Sort sort);

    // 게시판 검색
    Page<Post> findByTitleContaining(String keyword, Pageable pageable);

}
