package com.shachi.shachihouse.service;

import com.shachi.shachihouse.entities.Information;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface InformationService {

    Information save(Information information);
    Information update(Information information);
    Optional<Information> findById(Long id);
    void deleteById(Long id);
    List<Information> findAll();
    List<Information> findAll(Sort sort);
    Page<Information> findAll(Pageable pageable);
}
