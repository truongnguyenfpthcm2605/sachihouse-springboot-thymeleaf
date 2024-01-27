package com.shachi.shachihouse.service;

import com.shachi.shachihouse.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account save(Account account);
    Account update(Account account);
    Optional<Account> findById(Long id);
    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsername(String username);
    Optional<Account> findByUsernameAndProviderID(String username, String providerID);
    void deleteById(Long id);
    List<Account> findAll();
    List<Account> findAll(Sort sort);
    Page<Account> findAll(Pageable pageable);
}
