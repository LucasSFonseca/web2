package br.imd.controller;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.imd.model.Role;
import br.imd.model.User;
import br.imd.service.RoleService;
import br.imd.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	private static final String MSG_SUCESS_INSERT = "User inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "User successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted User successfully.";
	private static final String MSG_ERROR = "Error.";

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	

	@GetMapping
	public String index(Model model) {
		List<User> all = userService.findAll();
		model.addAttribute("listUsers", all);
		return "user/index";
	}
	
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			User user = userService.findOne(id).get();
			model.addAttribute("user", user);
		}
		return "user/show";
	}

	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute User entityUser) {
		// model.addAttribute("user", entityUser);

		List<Role> roles = roleService.findAll();
		model.addAttribute("roles", roles);
		
		return "user/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute User entity, BindingResult result, RedirectAttributes redirectAttributes) {
		User user = null;
		try {
			System.out.println("ROLES: " + entity.getRoles().size() );
			user = userService.save(entity);
			//redirectAttributes.addFlashAttribute("success", MSG_SUCESS_INSERT);
		} catch (Exception e) {
			System.out.println("Exception:: exception");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}catch (Throwable e) {
			System.out.println("Throwable:: exception");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}
		//return "redirect:/users/" + user.getId();
		return "redirect:/";
	}
	
	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		try {
			if (id != null) {
				User entity = userService.findOne(id).get();
				model.addAttribute("user", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "user/form";
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute User entity, BindingResult result, RedirectAttributes redirectAttributes) {
		User user = null;
		try {
			user = userService.save(entity);
			//redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		
		//return "redirect:/users/" + user.getId();
		return "redirect:/";
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				User entity = userService.findOne(id).get();
				userService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/users";
	}

}
