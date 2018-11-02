package com.skb.learn.spring.security.custom;

import com.skb.learn.spring.security.domain.entities.AutoUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String make;

    public String getMake() {
        return make;
    }

    public CustomAuthenticationToken(String principal, String credentials, String make) {
        super(principal, credentials);
        this.make = make;
    }

    // This constructor will be used once we have authenticated the user and now we want to return an Authencation
    // object within the Authentication Provider
    public CustomAuthenticationToken(AutoUser principal, String credentials,
                                     Collection<? extends GrantedAuthority> authorities, String make) {
        super(principal, credentials, authorities);
    }
}
