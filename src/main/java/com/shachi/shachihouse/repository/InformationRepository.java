package com.shachi.shachihouse.repository;

import com.shachi.shachihouse.entities.Information;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepository extends JpaRepository<Information,Long> {

    @Query("select o from Information o where o.email like :title or o.fullname like :title or o.phone like :title ")
    Page<Information> findAll(@Param("title") String title , Pageable pageable);

}
