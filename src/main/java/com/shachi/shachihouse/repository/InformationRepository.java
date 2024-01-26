package com.shachi.shachihouse.repository;

import com.shachi.shachihouse.entities.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepository extends JpaRepository<Information,Long> {
}
