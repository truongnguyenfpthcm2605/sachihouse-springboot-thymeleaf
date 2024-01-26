package com.shachi.shachihouse.service;

import com.shachi.shachihouse.entities.Role;
import com.shachi.shachihouse.utils.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role save(Role role);
    Role update(Role role);
    Optional<Role> findById(Long id);
    Optional<Role> findByName(Roles name);
    void deleteById(Long id);
    List<Role> findAll();
    List<Role> findAll(Sort sort);
    Page<Role> findAll(Pageable pageable);
}
