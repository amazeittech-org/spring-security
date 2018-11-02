package com.skb.learn.spring.security.web.controllers;

import com.skb.learn.spring.security.domain.entities.AutoUser;
import com.skb.learn.spring.security.domain.repositories.AutoUserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {

	private AutoUserRepository autoUserRepository;

	public HomeController(AutoUserRepository autoUserRepository) {
		this.autoUserRepository = autoUserRepository;
	}

	// Adding mapping to display/return our registration page
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String goRegister() {
		return "register";
	}

	// Adding mapping to actually perform user registration POST request from the registration.jsp page
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute AutoUser autoUser) {
		autoUser.setRole("ROLE_USER");
		autoUserRepository.save(autoUser);
		// We need to authenticate this new user. This user has not gone through Spring Security authenticaiton process
		// hence we need to authenticate the user programmatically with help in Authentication interface.

		// Authentication object is first created when the user attempts to login and it stores all of their credentials
		// And once the user is authenticated then the authentication object is placed in the Security Context.
		Authentication auth = new UsernamePasswordAuthenticationToken(autoUser, autoUser.getPassword(),
				autoUser.getAuthorities());

		// So, now after the user has been authenticated then we will place it in the Security Context
		SecurityContextHolder.getContext().setAuthentication(auth);

		// Now we need to redirect the user to the landing page or the root of the application
		return "redirect:/";
	}

	@RequestMapping(method=RequestMethod.GET)
	public String goHome(){
		return "home";
	}
	
	@RequestMapping("/services")
	public String goServices(){
		return "services";
	}

	// RequestMethod.GET will help us to distinguish between whether we are requesting (GET) the login form or we are
	// posting (POST) the login form
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String goLogin(){
		return "login";
	}
}
