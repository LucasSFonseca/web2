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
		List<Swap> all = swapService.findAll();
		model.addAttribute("listSwap", all);
		return "swap/index";
	}
	
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
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
		
		try{
			swap = (Swap) entity;
			System.out.println( "UserTo Swap: " + swap.getUserTo().getId() );
		} catch (Exception e) {
			System.out.println("Exception:: exception");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}catch (Throwable e) {
			System.out.println("Throwable:: exception");
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}
		
		return "redirect:/swaps/" + swap.getUserTo().getId() + "/" + swap.getUserFrom().getId();
	}
	
	@GetMapping("/{idUserTo}/{idUserFrom}")
	public String show(Model model, @PathVariable("idUserTo") Integer idUserTo, @PathVariable("idUserFrom") Integer idUserFrom, @ModelAttribute Swap entitySwap)
	{
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
		// model.addAttribute("swap", entitySwap);
		List<User> allUsers = userService.findAll();
		model.addAttribute("users", allUsers);
		return "swap/beforeswap";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Swap entity, BindingResult result, RedirectAttributes redirectAttributes) {
		Swap swap = null;
		try {
			swapService.swapBooks(entity);
			swap = swapService.save(entity);
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