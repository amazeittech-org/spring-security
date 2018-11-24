package com.skb.learn.spring.security.web.controllers;

import java.util.ArrayList;
import java.util.List;

import com.skb.learn.spring.security.domain.repositories.AutoUserRepository;
import com.skb.learn.spring.security.util.AppointmentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
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

import javax.annotation.security.RolesAllowed;


@Controller()
@RequestMapping("/appointments")
@Transactional
public class AppointmentController {

	private AppointmentRepository appointmentRepository;

	private AutoUserRepository autoUserRepository;

	private AppointmentUtils appointmentUtils;

	public AppointmentController(AppointmentRepository appointmentRepository, AutoUserRepository autoUserRepository,
								 AppointmentUtils appointmentUtils) {
		this.appointmentRepository = appointmentRepository;
		this.autoUserRepository = autoUserRepository;
		this.appointmentUtils = appointmentUtils;
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

	@ResponseBody
	@RequestMapping("/test")
	public String testPreFilter(Authentication auth) {
		AutoUser user = (AutoUser) auth.getPrincipal();
		AutoUser otherUser = new AutoUser();
		otherUser.setEmail("malicioususer@email.com");
		otherUser.setAutoUserId(100L);

		return appointmentUtils.saveAll(new ArrayList<Appointment>());
	}

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String getAppointmentPage(){
		return "appointments";
	}

	@ResponseBody
	@RequestMapping(value="/save", method=RequestMethod.POST)
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
	public List<Appointment> getAppointments(Authentication auth){
		/*List<Appointment> appointments = new ArrayList<>();
		this.appointmentRepository.findAll().forEach(apt -> appointments.add(apt));
		return appointments;*/
		return this.appointmentRepository.findByUser((AutoUser) auth);
	}

	@RequestMapping("/{appointmentId}")
	//@PostAuthorize("principal.autoUserId == #model[appointment].user.autoUserId")
					// It is evaluated AFTER the method is invoked. And this is how it is different from @PreAuthorize
					// This allows us to execute some unique kind of logic because we can look at the return object.
					// Since Model is a Map therefore we can use #model[key]
					// It is NOT common to user @PostAuthorize
	@PostAuthorize("returnObject == 'appointment'")
	public String getAppointment(@PathVariable("appointmentId") Long appointmentId, Model model){
		Appointment appointment = appointmentRepository.findOne(appointmentId);
		model.addAttribute("appointment", appointment);
		return "appointment";
	}

	@ResponseBody
	@RequestMapping("/confirm")
	@PreAuthorize("hasRole('ROLE_ADMIN')") // Annotation provided by SS. And it requires that the user has a certain
										   // role or permission BEFORE to invoking this method. The value inside can be
										   //  a SPEL expression.
										  // It is very commonly used method.
	public String confirm() {
		return "Confirmed";
	}

	@ResponseBody
	@RequestMapping("/cancel")
	@RolesAllowed("ROLE_ADMIN") // @RolesAllowed = @PreAuthorize.
								// With @RolesAllowed we canNOT use SPEL hence we just provide the role name which is
								// allowed to access the method. We can provide a comma separated list of authorities
								// like @RolesAllowed("ROLE_ADMIN, ROLE_USER")
	public String cancel() {
		return "Cancelled";
	}

	@ResponseBody
	@RequestMapping("/complete")
	public String complete() {
		return "Completed";
	}

}
