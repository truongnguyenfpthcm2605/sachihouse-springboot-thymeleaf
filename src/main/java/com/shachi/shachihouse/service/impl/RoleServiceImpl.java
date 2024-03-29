package com.shachi.shachihouse.service.impl;

import com.shachi.shachihouse.entities.Role;
import com.shachi.shachihouse.repository.RoleRepository;
import com.shachi.shachihouse.service.RoleService;
import com.shachi.shachihouse.utils.Roles;
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
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
    @Override
    @Transactional(
            propagation = Propagation.REQUIRED
            , isolation = Isolation.READ_COMMITTED,
            rollbackFor = {Exception.class, Throwable.class}
    )
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED
            , isolation = Isolation.READ_COMMITTED,
            rollbackFor = {Exception.class, Throwable.class}
    )
    public Role update(Role role) {
        return repository.save(role);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Role> findByName(Roles name) {
        return repository.findByName(name);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED
            , isolation = Isolation.READ_COMMITTED,
            rollbackFor = {Exception.class, Throwable.class}
    )
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Role> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
