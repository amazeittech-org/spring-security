package com.skb.learn.spring.security.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skb.learn.spring.security.domain.entities.AutoUser;
import org.springframework.stereotype.Repository;

@Repository()
public interface AutoUserRepository extends JpaRepository<AutoUser, Long> {

    public AutoUser findByUsername(String username);
}
