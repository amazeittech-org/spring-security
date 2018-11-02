package com.skb.learn.spring.security.custom;

import com.skb.learn.spring.security.domain.entities.AutoUser;
import com.skb.learn.spring.security.domain.repositories.AutoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private AutoUserRepository autoUserRepository;

    public CustomUserDetailsService(AutoUserRepository autoUserRepository) {
        super();
        this.autoUserRepository = autoUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AutoUser autoUser = autoUserRepository.findByUsername(username);
        return autoUser;
       /* return new User(autoUser.getUsername(), autoUser.getPassword(),
                AuthorityUtils.createAuthorityList(autoUser.getRole()));*/
    }
}
