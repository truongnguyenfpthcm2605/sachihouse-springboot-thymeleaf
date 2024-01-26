package com.shachi.shachihouse.repository;

import com.shachi.shachihouse.entities.Role;
import com.shachi.shachihouse.utils.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(Roles name);
}
