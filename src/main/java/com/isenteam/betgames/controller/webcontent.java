package com.isenteam.betgames.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class webcontent {
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = { "/commandes" }, method = RequestMethod.GET)
	public String personList() {
		return "commandes";
	}
}