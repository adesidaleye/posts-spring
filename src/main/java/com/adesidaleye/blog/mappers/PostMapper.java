package com.adesidaleye.blog.mappers;

import com.adesidaleye.blog.domain.CreatePostRequest;
import com.adesidaleye.blog.domain.UpdatePostRequest;
import com.adesidaleye.blog.domain.dtos.CreatePostRequestDto;
import com.adesidaleye.blog.domain.dtos.PostDto;
import com.adesidaleye.blog.domain.dtos.UpdatePostRequestDto;
import com.adesidaleye.blog.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "category", source = "category")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "readingTime", source = "readingTime")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto createPostRequestDto);

    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto updatePostRequestDto);
}