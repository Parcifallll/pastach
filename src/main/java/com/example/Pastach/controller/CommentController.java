package com.example.Pastach.controller;

import com.example.Pastach.dto.comment.CommentCreateDTO;
import com.example.Pastach.dto.comment.CommentResponseDTO;
import com.example.Pastach.dto.comment.CommentUpdateDTO;
import com.example.Pastach.model.User;
import com.example.Pastach.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDTO> create(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateDTO dto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(commentService.create(postId, dto, user));
    }

    @GetMapping
    public ResponseEntity<Page<CommentResponseDTO>> getAllByPostId(
            @PathVariable Long postId,
            Pageable pageable) {
        return ResponseEntity.ok(commentService.getAllByPostId(postId, pageable));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> update(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateDTO dto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(commentService.updateById(commentId, dto, user));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal User user) {
        commentService.deleteById(commentId, user);
        return ResponseEntity.noContent().build();
    }
}