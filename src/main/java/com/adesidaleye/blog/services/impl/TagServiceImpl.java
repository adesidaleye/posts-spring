package com.adesidaleye.blog.services.impl;

import com.adesidaleye.blog.domain.entities.Tag;
import com.adesidaleye.blog.repositories.TagRepository;
import com.adesidaleye.blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAllWithPostCount();
    }

    @Transactional
    @Override
    // Fetch and/or create tags, if created post has new and existing tags, find all tags, check which currently do not exist and create Tag entities
    public List<Tag> createTags(Set<String> tagNames) {
        List<Tag> existingTags = tagRepository.findByNameIn(tagNames);

        Set<String> existingTagNames = existingTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        // Only the names NOT found in the DB become new Tag entities
        List<Tag> newTags = tagNames.stream()
                .filter(name -> !existingTagNames.contains(name))
                .map(name -> Tag.builder()
                        .name(name)
                        .posts(new HashSet<>())
                        .build()
                )
                .toList();

        // Batch-insert the new tags and skip the call if there's nothing new.
        List<Tag> savedTags = new ArrayList<>();
        if (!newTags.isEmpty()) {
            savedTags = tagRepository.saveAll(newTags);
        }

        // Return both freshly created tags and already existing tags, so client has complete list of Tag entities to attach to post
        savedTags.addAll(existingTags);

        return savedTags;
    }

    @Transactional
    @Override
    public void deleteTag(UUID id) {
        tagRepository.findById(id)
                .ifPresent(tag -> {
                    if (!tag.getPosts().isEmpty()) {
                        throw new IllegalStateException("Cannot delete tag with posts");
                    }

                    tagRepository.deleteById(id);
                });
    }
}