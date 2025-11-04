package com.example.Pastach.controller;

import com.example.Pastach.dto.post.PostCreateDTO;
import com.example.Pastach.dto.post.PostResponseDTO;
import com.example.Pastach.dto.post.PostUpdateDTO;
import com.example.Pastach.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    /* with pagination
    http://localhost:8080/posts?page=5&size=20&sort=photoUrl,desc
    http://localhost:8080/posts
    http://localhost:8080/posts?page=2&size=1
     */
    @GetMapping
    public Page<PostResponseDTO> getAllPaged(@PageableDefault(size = 10, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.getAll(pageable);
    }

    @GetMapping("/users/{authorId}/posts")
    public Page<PostResponseDTO> getByAuthorId(@PathVariable String authorId,
                                               @PageableDefault(size = 10, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.getByAuthorId(authorId, pageable);
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
