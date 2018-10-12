package com.skb.learn.spring.security.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {

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
