package br.imd.controller;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import br.imd.model.Book;
import br.imd.model.Swap;
import br.imd.model.User;
import br.imd.service.BookService;
import br.imd.service.SwapService;
import br.imd.service.UserService;

@Controller
@RequestMapping("/swaps")
public class SwapController {

	private static final String MSG_SUCESS_INSERT = "Swap inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "Swap successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted Swap successfully.";
	private static final String MSG_ERROR = "Error.";

	@Autowired
	private SwapService swapService;

	@Autowired
	private UserService userService;
	@Autowired
	private BookService bookService;
	
	@GetMapping
	public String index(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    User user = userService.findByLogin(name);
		model.addAttribute("user", user);
		
		List<Swap> all = swapService.findByUser(user.getId());
		model.addAttribute("listSwap", all);
		return "swap/index";
	}
	
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    User user = userService.findByLogin(name);
		model.addAttribute("user", user);
		
		if (id != null) {
			Swap swap = swapService.findOne(id).get();
			model.addAttribute("swap", swap);
		}
		return "swap/show";
	}
	
	@PostMapping("/beforeswap")
	public String redirectFromSwap(@Valid @ModelAttribute Swap entity, BindingResult result, RedirectAttributes redirectAttributes)
	{
		Swap swap = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    User user = userService.findByLogin(name);
		
		swap = entity;
		
		return "redirect:/swaps/" + user.getId() + "/" + swap.getUserFrom().getId();
	}
	
	@GetMapping("/{idUserTo}/{idUserFrom}")
	public String show(Model model, @PathVariable("idUserTo") Integer idUserTo, @PathVariable("idUserFrom") Integer idUserFrom, @ModelAttribute Swap entitySwap)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    User user = userService.findByLogin(name);
		model.addAttribute("user", user);
		
		if (idUserTo != null && idUserFrom != null)
		{
			User userTo = userService.findOne(idUserTo).get();
			model.addAttribute("userTo", userTo);
			
			User userFrom = userService.findOne(idUserFrom).get();
			model.addAttribute("userFrom", userFrom);

			List<Book> booksTo = bookService.findByCollections(userTo.getUsuarioTem());
			model.addAttribute("booksTo", booksTo);
			
			List<Book> booksFrom = bookService.findByCollections(userFrom.getUsuarioTem());
			model.addAttribute("booksFrom", booksFrom);
		}
		return "swap/form";
	}

	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Swap entitySwap) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    User user = userService.findByLogin(name);
		model.addAttribute("user", user);
		// model.addAttribute("swap", entitySwap);
		List<User> allUsers = userService.findAllExcept(user.getId());
		model.addAttribute("users", allUsers);
		return "swap/beforeswap";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Swap entity, BindingResult result, RedirectAttributes redirectAttributes) {
		Swap swap = null;
	    
		try {	

			if( entity.getUserTo() == null )
				System.out.println("USER TO NULL");
			
			if( entity.getUserFrom() == null )
				System.out.println("USER FROM NULL");
			
			if( entity.getBookTo() == null )
				System.out.println("BOOK TO NULL");
			
			if( entity.getBookFrom() == null )
				System.out.println("BOOK FROM NULL");
				
			
			swap = swapService.swapBooks(entity);
			
			System.out.println("TO: " + swap.getUserTo().getNome());
			System.out.println("TO: " + swap.getUserFrom().getNome());
			
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_INSERT);
		} catch (Exception e) {
			System.out.println("Exception:: exception");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}catch (Throwable e) {
			System.out.println("Throwable:: exception");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}
		
		return "redirect:/swaps/" + swap.getId();
	}
	
	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    User user = userService.findByLogin(name);
		model.addAttribute("user", user);
		
		try {
			if (id != null) {
				Swap entity = swapService.findOne(id).get();
				model.addAttribute("swap", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "swap/form";
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Swap entity, BindingResult result, RedirectAttributes redirectAttributes) {
		Swap swap = null;
		try {
			swap = swapService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/swaps/" + swap.getId();
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				Swap entity = swapService.findOne(id).get();
				swapService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/swaps";
	}

}
