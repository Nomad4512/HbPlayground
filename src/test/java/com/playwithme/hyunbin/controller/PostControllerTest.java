package com.playwithme.hyunbin.controller;

import com.playwithme.hyunbin.domain.Post;
import com.playwithme.hyunbin.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    void testGetAllPosts() throws Exception {
        when(postService.getAllpostsByPage(anyInt(), anyInt())).thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(get("/board"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/list"));
    }

    @Test
    void testSearch() throws Exception {
        when(postService.search(anyString(), any())).thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(get("/board/search").param("keyword", "test"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/list"));
    }

    @Test
    void testViewPost() throws Exception {
        when(postService.getPostById(anyLong())).thenReturn(new Post());

        mockMvc.perform(get("/board/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/read"));
    }

    @Test
    void testCreatePost() throws Exception {
        when(postService.save(any())).thenReturn(new Post());

        mockMvc.perform(post("/board/posts")
                        .param("title", "test")
                        .param("writer", "author")
                        .param("pass", "password")
                        .param("content", "content"))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdatePost() throws Exception {
        when(postService.getPostById(anyLong())).thenReturn(new Post());

        mockMvc.perform(put("/board/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "Updated title")
                        .param("writer", "Updated author")
                        .param("pass", "Updated password")
                        .param("content", "Updated content"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePost() throws Exception {
        when(postService.getPostById(anyLong())).thenReturn(new Post());

        mockMvc.perform(delete("/board/1"))
                .andExpect(status().isOk());
    }

}
