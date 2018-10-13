package com.skb.learn.spring.security.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skb.learn.spring.security.domain.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
