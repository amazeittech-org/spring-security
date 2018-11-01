package com.skb.learn.spring.security.web.controllers;

import java.util.ArrayList;
import java.util.List;

import com.skb.learn.spring.security.domain.repositories.AutoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skb.learn.spring.security.domain.entities.Appointment;
import com.skb.learn.spring.security.domain.entities.AutoUser;
import com.skb.learn.spring.security.domain.repositories.AppointmentRepository;


@Controller()
@RequestMapping("/appointments")
@Transactional
public class AppointmentController {

	private AppointmentRepository appointmentRepository;

	private AutoUserRepository autoUserRepository;

	public AppointmentController(AppointmentRepository appointmentRepository, AutoUserRepository autoUserRepository) {
		this.appointmentRepository = appointmentRepository;
		this.autoUserRepository = autoUserRepository;
	}

	@ModelAttribute
	public Appointment getAppointment(){
		return new Appointment();
	}

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String getAppointmentPage(){
		return "appointments";
	}

	@ResponseBody
	@RequestMapping(value="/", method=RequestMethod.POST)
	public List<Appointment> saveAppointment(@ModelAttribute Appointment appointment){
		AutoUser user = (AutoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// AutoUser user = this.autoUserRepository.findByUsername(username);
		//Appointment newApt = new Appointment();

		appointment.setUser(user);
		appointment.setStatus("Initial");
		appointmentRepository.save(appointment);
		List<Appointment> appointments = new ArrayList<>();
		this.appointmentRepository.findAll().forEach(apt -> appointments.add(apt));
		return appointments;
	}
	
	@ResponseBody
	@RequestMapping("/all")
	public List<Appointment> getAppointments(){
		List<Appointment> appointments = new ArrayList<>();
		this.appointmentRepository.findAll().forEach(apt -> appointments.add(apt));
		return appointments;
	}

	@RequestMapping("/{appointmentId}")
	public String getAppointment(@PathVariable("appointmentId") Long appointmentId, Model model){
		Appointment appointment = appointmentRepository.findOne(appointmentId);
		model.addAttribute("appointment", appointment);
		return "appointment";
	}
	
}
