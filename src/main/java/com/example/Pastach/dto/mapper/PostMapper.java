package com.example.Pastach.dto.mapper;

import com.example.Pastach.dto.post.PostCreateDTO;
import com.example.Pastach.dto.post.PostResponseDTO;
import com.example.Pastach.dto.post.PostUpdateDTO;
import com.example.Pastach.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
// user do not update value (null) -> value is not updated (PATCH instead of PUT)
public interface PostMapper {
    Post toEntity(PostCreateDTO postCreateDTO, String authorId);

    PostResponseDTO toResponseDto(Post post);

    void updateFromDto(PostUpdateDTO postUpdateDTO, Post post);

}