package com.shachi.shachihouse.service;

import com.shachi.shachihouse.entities.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HouseService {
    House save(House house);
    House update(House house);
    Optional<House> findById(String id);
    void deleteById(String id);
    List<House> findAll();
    List<House> findAll(Sort sort);
    Page<House> findAll(Pageable pageable);
    Page<House> findByKeyword(String keyword,String title, Pageable pageable);
    List<House> findByCategoryId(Long categoryId);
    List<House> searchByBedrooms(int bedrooms, Long categoryId);

    List<House> searchByCustomer(@Param("customer") String customer, Long categoryId);


}
