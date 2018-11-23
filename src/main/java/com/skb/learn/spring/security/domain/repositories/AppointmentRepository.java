package com.skb.learn.spring.security.domain.repositories;

import com.skb.learn.spring.security.domain.entities.AutoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import com.skb.learn.spring.security.domain.entities.Appointment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    public List<Appointment> findByUser(AutoUser user);
}
