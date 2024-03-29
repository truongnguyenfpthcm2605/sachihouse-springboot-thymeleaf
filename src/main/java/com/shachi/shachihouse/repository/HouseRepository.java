package com.shachi.shachihouse.repository;

import com.shachi.shachihouse.entities.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House,String> {
    @Query("select o from House o where (o.title LIKE :keyword or o.id like :keyword) and o.category.title like :title and o.isactive = true")
    Page<House> findByKeyword(@Param("keyword") String keyword, @Param("title") String title, Pageable pageable);

    List<House> findByCategoryId(Long categoryId);

    @Query("select o from House  o where o.bedroom = :bedroom and o.category.id = :categoryId")
    List<House> findByBedroom(@Param("bedroom") String bedroom ,@Param("categoryId") Long categoryId);

    @Query("select o from House  o where o.customer like :customer and o.category.id = :categoryId")
    List<House> searchByCustomer(@Param("customer") String customer, Long categoryId);




}
