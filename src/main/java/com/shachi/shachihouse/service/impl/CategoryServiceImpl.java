package com.shachi.shachihouse.service.impl;

import com.shachi.shachihouse.entities.Category;
import com.shachi.shachihouse.repository.CategoryRepository;
import com.shachi.shachihouse.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryService;

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED
            , isolation = Isolation.READ_COMMITTED,
            rollbackFor = {Exception.class, Throwable.class}
    )
    public Category save(Category category) {
        return categoryService.save(category);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED
            , isolation = Isolation.READ_COMMITTED,
            rollbackFor = {Exception.class, Throwable.class}
    )
    public Category update(Category category) {
        return categoryService.save(category);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryService.findById(id);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED
            , isolation = Isolation.READ_COMMITTED,
            rollbackFor = {Exception.class, Throwable.class}
    )
    public void deleteById(Long id) {
            categoryService.deleteById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @Override
    public List<Category> findAll(Sort sort) {
        return categoryService.findAll(sort);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }
}
