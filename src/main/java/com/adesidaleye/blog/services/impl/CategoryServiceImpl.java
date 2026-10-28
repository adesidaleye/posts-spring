package com.adesidaleye.blog.services.impl;

import com.adesidaleye.blog.domain.entities.Category;
import com.adesidaleye.blog.repositories.CategoryRepository;
import com.adesidaleye.blog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount();
    }
}
