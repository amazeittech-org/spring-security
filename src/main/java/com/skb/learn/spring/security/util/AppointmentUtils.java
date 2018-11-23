package com.skb.learn.spring.security.util;

import com.skb.learn.spring.security.domain.entities.Appointment;
import com.skb.learn.spring.security.domain.entities.AutoUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppointmentUtils {

    public String saveAll(List<Appointment> appointments) {
        StringBuilder sb = new StringBuilder();
        for(Appointment appointment : appointments) {
            sb.append(appointment.getUser().getEmail());
            sb.append(" ");
        }
        return  sb.toString();
    }

    public static Appointment createAppointment(AutoUser user) {
        Appointment appointment = new Appointment();
        appointment.setUser(user);
        return appointment;
    }
}
