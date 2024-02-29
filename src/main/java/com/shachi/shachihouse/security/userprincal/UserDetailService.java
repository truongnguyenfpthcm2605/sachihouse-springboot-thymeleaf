package com.shachi.shachihouse.security.userprincal;

import com.shachi.shachihouse.entities.Account;
import com.shachi.shachihouse.service.impl.AccountServiceImpl;
import com.shachi.shachihouse.utils.Common;
import com.shachi.shachihouse.utils.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final AccountServiceImpl accountService;
    private final Session session;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
        session.setAttribute(Common.ACCOUNT_SESSION,account);
        return new UserPrinciple(account);
    }


}
