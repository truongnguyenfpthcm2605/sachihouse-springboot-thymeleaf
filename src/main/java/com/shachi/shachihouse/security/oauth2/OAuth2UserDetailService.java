package com.shachi.shachihouse.security.oauth2;

import com.shachi.shachihouse.entities.Account;
import com.shachi.shachihouse.entities.Role;
import com.shachi.shachihouse.service.impl.AccountServiceImpl;
import com.shachi.shachihouse.service.impl.RoleServiceImpl;
import com.shachi.shachihouse.utils.Common;
import com.shachi.shachihouse.utils.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OAuth2UserDetailService extends DefaultOAuth2UserService {

    private final AccountServiceImpl accountService;
    private final RoleServiceImpl roleService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            return checkOAuth2User(userRequest,oAuth2User);
        }catch (AuthenticationException e){
           throw e;
        }catch (Exception ex){
            throw new InternalAuthenticationServiceException(ex.getMessage());
        }
    }

    private OAuth2User checkOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        // get OAuth2 can is Google or Facebook or Github
        OAuth2Userdetails oAuth2Userdetails = OAuth2UserDetailFactory.getOAuth2Userdetails(
                oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );

        if (Objects.isNull(oAuth2Userdetails)){
            return null;
        }

        Account accountdetail ;
        Optional<Account> account = accountService.findByUsernameAndProviderID(
                oAuth2Userdetails.getEmail(),
                oAuth2UserRequest.getClientRegistration().getRegistrationId());
        // if account exists
        if (account.isPresent()) {
            accountdetail = updateOAuth2Userdetail(account.get(),oAuth2Userdetails);
        }else{
            accountdetail = registerNewOAuth2Userdetail(oAuth2UserRequest,oAuth2Userdetails);
        }
        Common.email_OAuth2 = accountdetail.getEmail();
        Common.providerId = accountdetail.getProviderID();

        // return account in system
        return new OAuth2UserDetailCustom(
                accountdetail.getFullname(),
                accountdetail.getEmail(),
                accountdetail.getPassword(),
                accountdetail.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList()));

    }


    // add user OAuth2 in Database
    public Account registerNewOAuth2Userdetail(OAuth2UserRequest oAuth2UserRequest, OAuth2Userdetails oAuth2Userdetails){
        Set<Role> role = new HashSet<>();
        role.add(roleService.findByName(Roles.USER).get());
        Account account = Account.builder()
                .username(oAuth2Userdetails.getEmail())
                .email(oAuth2Userdetails.getEmail())
                .fullname(oAuth2Userdetails.getName())
                .createdate(LocalDate.now())
                .providerID(oAuth2UserRequest.getClientRegistration().getRegistrationId())
                .isactive(true)
                .password("N/A")
                .roles(role)
                .updatedate(LocalDate.now()).build();
        return  accountService.save(account);

    }


    // update account OAuth2
    public Account updateOAuth2Userdetail(Account account, OAuth2Userdetails oAuth2Userdetails){
        account.setUsername(oAuth2Userdetails.getEmail());
        account.setUpdatedate(LocalDate.now());
        return accountService.update(account);
    }
}


