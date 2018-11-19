package com.skb.learn.spring.security.web.controllers;

import java.util.ArrayList;
import java.util.List;

import com.skb.learn.spring.security.domain.repositories.AutoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
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

	// This approach is more useful than using access and url in the JSP authorize tags. By using this we now need to
	// mention allowed Role or Authority just in this place, and not in JSP page
	@ModelAttribute("isUser")
	private boolean isUser(Authentication auth) {
		return auth != null &&
				auth.getAuthorities().contains(AuthorityUtils.createAuthorityList("ROLE_USER").get(0));
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

	@ResponseBody
	@RequestMapping("/confirm")
	@PreAuthorize("hasRole('ROLE_ADMIN')") // Annotation provided by SS. And it requires that the user has a certain
										  // role or permission prior to invoking this method. The value inside can be
										 //  a SPEL expression.
	public String confirm() {
		return "Confirmed";
	}

	@ResponseBody
	@RequestMapping("/cancel")
	@PreAuthorize("hasRole('ROLE_ADMIN')") // This will ensure that only an Admin can call this API
	public String cancel() {
		return "Cancelled";
	}

	@ResponseBody
	@RequestMapping("/complete")
	public String complete() {
		return "Completed";
	}

}
