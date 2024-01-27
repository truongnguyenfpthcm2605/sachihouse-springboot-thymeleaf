package com.shachi.shachihouse.service;

import com.shachi.shachihouse.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category save(Category category);
    Category update(Category category);
    Optional<Category> findById(Long id);
    void deleteById(Long id);
    List<Category> findAll();
    List<Category> findAll(Sort sort);
    Page<Category> findAll(Pageable pageable);
}
