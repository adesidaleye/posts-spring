package com.adesidaleye.blog.controllers;

import com.adesidaleye.blog.domain.dtos.CreateTagRequest;
import com.adesidaleye.blog.domain.dtos.TagResponse;
import com.adesidaleye.blog.domain.entities.Tag;
import com.adesidaleye.blog.mappers.TagMapper;
import com.adesidaleye.blog.services.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<Tag> tags = tagService.getTags();

        List<TagResponse> tagResponses = tags.stream()
                .map(tagMapper::toDto)
                .toList();

        return ResponseEntity.ok(tagResponses);
    }

    @PostMapping
    public ResponseEntity<List<TagResponse>> createTags(@Valid @RequestBody CreateTagRequest createTagRequest) {
        List<Tag> savedTags = tagService.createTags(createTagRequest.getNames());
        List<TagResponse> createdTagResponses = savedTags.stream()
                .map(tagMapper::toDto)
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdTagResponses);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
