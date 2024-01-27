package com.shachi.shachihouse.service.impl;

import com.shachi.shachihouse.entities.Information;
import com.shachi.shachihouse.repository.InformationRepository;
import com.shachi.shachihouse.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InformationServiceImpl implements InformationService {
    private final InformationRepository informationService;

    @Override
    public Information save(Information information) {
        return informationService.save(information);
    }

    @Override
    public Information update(Information information) {
        return informationService.save(information);
    }

    @Override
    public Optional<Information> findById(Long id) {
        return informationService.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        informationService.deleteById(id);
    }

    @Override
    public List<Information> findAll() {
        return informationService.findAll();
    }

    @Override
    public List<Information> findAll(Sort sort) {
        return informationService.findAll(sort);
    }

    @Override
    public Page<Information> findAll(Pageable pageable) {
        return informationService.findAll(pageable);
    }
}
