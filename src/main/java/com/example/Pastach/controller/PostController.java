package com.example.Pastach.controller;

import com.example.Pastach.dto.post.PostCreateDTO;
import com.example.Pastach.dto.post.PostResponseDTO;
import com.example.Pastach.dto.post.PostUpdateDTO;
import com.example.Pastach.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDTO create(@Valid @RequestBody PostCreateDTO dto, @RequestParam String authorId) {
        return postService.create(dto, authorId);
    }

    @GetMapping
    public Collection<PostResponseDTO> getAll() {
        return postService.getAll();
    }

    @GetMapping("/user/{userId}") // http://localhost:8080/posts/12
    public Collection<PostResponseDTO> getByAuthorId(@PathVariable String userId) {
        return postService.getByAuthorId(userId);
    }

    @PatchMapping("/{postId}")
    public PostResponseDTO updateById(@PathVariable int postId, @Valid @RequestBody PostUpdateDTO dto) {
        return postService.updateById(postId, dto);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int postId) {
        postService.deleteById(postId);
    }

//    @GetMapping("posts/search") // http://localhost:8080/posts/search?author=Roman
//    public Collection<Post> searchPosts(@RequestParam String author, @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate creationDate) {
//        return postService.searchPosts(author, creationDate);
//    }

}
