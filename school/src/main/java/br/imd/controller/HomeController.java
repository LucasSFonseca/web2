package br.imd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.imd.model.User;
import br.imd.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;
    
	@RequestMapping("/")
	public String home(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    User user = userService.findByLogin(name);
		model.addAttribute("user", user);
		
		return "home";
	}
	
}
