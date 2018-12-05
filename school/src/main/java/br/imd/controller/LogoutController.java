package br.imd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LogoutController
{
	@PostMapping(value = "/logout")
	public String create(){
		return "redirect:/login";
	}
}
