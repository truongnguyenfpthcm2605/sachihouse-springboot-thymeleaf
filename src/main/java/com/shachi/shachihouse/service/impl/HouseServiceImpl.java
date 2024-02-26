package com.shachi.shachihouse.service.impl;

import com.shachi.shachihouse.entities.House;
import com.shachi.shachihouse.repository.HouseRepository;
import com.shachi.shachihouse.service.HouseService;
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
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED
            , isolation = Isolation.READ_COMMITTED,
            rollbackFor = {Exception.class, Throwable.class}
    )
    public House save(House house) {
        return houseRepository.save(house);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED
            , isolation = Isolation.READ_COMMITTED,
            rollbackFor = {Exception.class, Throwable.class}
    )
    public House update(House house) {
        return houseRepository.save(house);
    }

    @Override
    public Optional<House> findById(String id) {
        return houseRepository.findById(id);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED
            , isolation = Isolation.READ_COMMITTED,
            rollbackFor = {Exception.class, Throwable.class}
    )
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

    @Override
    public Page<House> findByKeyword(String keyword, String title, Pageable pageable) {
        return houseRepository.findByKeyword("%" + keyword + "%", "%" + title + "%", pageable);
    }

    @Override
    public List<House> findByCategoryId(Long categoryId) {
        return houseRepository.findByCategoryId(categoryId);
    }


    @Override
    public List<House> searchByBedrooms(int bedrooms) {
        String bedroomsString = String.valueOf(bedrooms);
        return houseRepository.findByBedroom(bedroomsString);
    }




}
