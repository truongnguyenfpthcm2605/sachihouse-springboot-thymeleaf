package com.shachi.shachihouse.service.impl;

import com.shachi.shachihouse.entities.House;
import com.shachi.shachihouse.repository.HouseRepository;
import com.shachi.shachihouse.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    @Override
    public House save(House house) {
        return houseRepository.save(house);
    }

    @Override
    public House update(House house) {
        return houseRepository.save(house);
    }

    @Override
    public Optional<House> findById(String id) {
        return houseRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
            houseRepository.deleteById(id);
    }

    @Override
    public List<House> findAll() {
        return houseRepository.findAll();
    }

    @Override
    public List<House> findAll(Sort sort) {
        return houseRepository.findAll(sort);
    }

    @Override
    public Page<House> findAll(Pageable pageable) {
        return houseRepository.findAll(pageable);
    }
}
