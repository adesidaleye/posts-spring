package com.adesidaleye.blog.repositories;

import com.adesidaleye.blog.domain.entities.Category;
import com.adesidaleye.blog.domain.entities.Post;
import com.adesidaleye.blog.domain.entities.Tag;
import com.adesidaleye.blog.domain.entities.User;
import com.adesidaleye.blog.domain.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag);

    List<Post> findAllByStatusAndCategory(PostStatus status, Category category);

    List<Post> findAllByStatusAndTagsContaining(PostStatus status, Tag tag);

    List<Post> findAllByStatus(PostStatus status);

    List<Post> findAllByAuthorAndStatus(User author, PostStatus status);
}
