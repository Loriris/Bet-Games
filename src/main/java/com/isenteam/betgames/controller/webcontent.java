package com.isenteam.betgames.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class webcontent {
	
	@RequestMapping(value = { "/", "/accueil" }, method = RequestMethod.GET)
	public String accueil() {
		return "accueil";
	}

	@RequestMapping(value = { "/documentation" }, method = RequestMethod.GET)
	public String documentation() {
		return "documentation";
	}
	
	@RequestMapping(value = { "en/home" }, method = RequestMethod.GET)
	public String home() {
		return "en/home";
	}
	
	@RequestMapping(value = { "en/documentation" }, method = RequestMethod.GET)
	public String endocumentation() {
		return "en/documentation";
	}
}
