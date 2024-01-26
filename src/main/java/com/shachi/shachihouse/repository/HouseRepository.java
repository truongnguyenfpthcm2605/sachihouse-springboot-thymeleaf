package com.shachi.shachihouse.repository;

import com.shachi.shachihouse.entities.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House,String> {
}
