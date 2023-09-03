package com.playwithme.hyunbin.service;

import com.playwithme.hyunbin.domain.Post;
import com.playwithme.hyunbin.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllpostsByPage() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("boardId").descending());
        when(postRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(new Post(), new Post())));

        postService.getAllpostsByPage(1, 10);

        verify(postRepository).findAll(pageable);
    }

    @Test
    void search() {
        Pageable pageable = PageRequest.of(0, 10);
        when(postRepository.findByTitleContaining(anyString(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(new Post())));

        postService.search("test", pageable);

        verify(postRepository).findByTitleContaining("test", pageable);
    }

    @Test
    void getPostById() {
        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Post()));

        postService.getPostById(1L);

        verify(postRepository).findById(1L);
    }

    @Test
    void save() {
        Post post = new Post();
        when(postRepository.saveAndFlush(any(Post.class))).thenReturn(post);

        postService.save(post);

        verify(postRepository).saveAndFlush(post);
    }

    @Test
    void deletePost() {
        doNothing().when(postRepository).deleteById(anyLong());

        postService.deletePost(1L);

        verify(postRepository).deleteById(1L);
    }
}
