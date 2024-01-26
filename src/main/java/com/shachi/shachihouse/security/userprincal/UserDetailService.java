package com.shachi.shachihouse.security.userprincal;

import com.shachi.shachihouse.entities.Account;
import com.shachi.shachihouse.service.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final AccountServiceImpl accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
        return new UserPrinciple(account);
    }


}
