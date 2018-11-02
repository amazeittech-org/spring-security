package com.skb.learn.spring.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    public static void main(String[] args) {

        BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
        String password = bcryptEncoder.encode("password");
        System.out.println("Encrypted password: " + password);

        // $2a$10$pk8BeeASQ/jpp6yZlYkBPOC9RXPqFWzdcMKLYc1TT1.XGfte1nnJO
    }
}
