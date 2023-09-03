package com.playwithme.hyunbin.controller;

import com.playwithme.hyunbin.domain.Post;
import com.playwithme.hyunbin.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/board")
public class PostController {

    @Autowired
    private PostService postService;

    // 전체 목록 조회
    @GetMapping("")
    public String post(Model model,
                         @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                         @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Page<Post> postPage = postService.getAllpostsByPage(page, pageSize);
        List<Post> postList = postPage.getContent();

        model.addAttribute("postList", postPage.getContent());
        model.addAttribute("page", postPage.getNumber() + 1);
        model.addAttribute("totalPages", postPage.getTotalPages());
        return "board/list";
    }

    // 검색 후 목록 조회
    @GetMapping("/search")
    public String search(@RequestParam(name = "keyword", required = false) String keyword,
                         @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                         @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                         Model model) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "boardId"));
        Page<Post> searchList = postService.search(keyword, pageable);

        model.addAttribute("searchList", searchList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", searchList.hasNext());
        model.addAttribute("hasPrev", searchList.hasPrevious());
        model.addAttribute("page", searchList.getNumber() + 1);
        model.addAttribute("totalPages", searchList.getTotalPages());

        return "board/list";
    }

    // 특정 게시글 조회
    @GetMapping("/{board_id}")
    public String viewPost(@PathVariable("board_id") Long id, Model model){

        Post post = postService.getPostById(id);
        model.addAttribute("post", post);

        return "board/read";
    }

    // 게시글 등록 폼 이동
    @GetMapping("/posts")
    public String post(){
        return "board/post";
    }

    // 게시글 등록
    @PostMapping("/posts")
    public ResponseEntity<String> createPost(@RequestParam("title") String title,
                                             @RequestParam("pass") String password,
                                             @RequestParam("content") String content,
                                             @RequestParam("writer") String writer) {
        Post post = new Post();
        post.setTitle(title);
        post.setWriter(writer);
        post.setPassword(password);
        post.setContent(content);
        post.setCreatedDate(LocalDateTime.now());

        postService.save(post);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/board")
                .body("게시글을 등록했습니다.");
    }

    // 게시글 수정 폼 이동
    @GetMapping("/{board_id}/edit")
    public String updatePost(@PathVariable("board_id") Long boardId, Model model) {
        Post post = postService.getPostById(boardId);
        model.addAttribute("post", post);

        return "board/update";
    }

    // 게시글 수정
    @PutMapping("/{board_id}")
    public ResponseEntity<String> updatePost(@PathVariable("board_id") Long id,
                                             @ModelAttribute Post updatedPost) {

        Post existingPost = postService.getPostById(id);

        if (existingPost == null) {
            return new ResponseEntity<>("게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setWriter(updatedPost.getWriter());
        existingPost.setPassword(updatedPost.getPassword());
        existingPost.setContent(updatedPost.getContent());
        existingPost.setModifiedDate(LocalDateTime.now());

        postService.save(existingPost);

        return ResponseEntity.status(HttpStatus.OK).header("Location", "/board")
                .body("게시글이 수정되었습니다.");
    }


    // 게시글 삭제
    @DeleteMapping("{board_id}")
    public ResponseEntity<String> deletePost(@PathVariable("board_id") Long id) {
        Post existingPost = postService.getPostById(id);

        if (existingPost == null) {
            return new ResponseEntity<>("게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        postService.deletePost(id);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Location", "/board")
                .body("게시글이 삭제되었습니다.");
    }

}
