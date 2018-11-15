package com.example.controller;

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

import com.example.model.Book;
import com.example.model.Collection;
import com.example.model.User;
import com.example.service.BookService;
import com.example.service.CollectionService;
import com.example.service.ModuleService;
import com.example.service.UserService;

@Controller
@RequestMapping("/collections")
public class CollectionController {

	private static final String MSG_SUCESS_INSERT = "Collection inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "Collection successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted Collection successfully.";
	private static final String MSG_ERROR = "Error.";

	@Autowired
	private CollectionService collectionService;
	@Autowired
	private UserService userService; //user service
	@Autowired
	private BookService bookService; //book service
	

	@GetMapping
	public String index(Model model) {
		List<Collection> all = collectionService.findAll();
		model.addAttribute("listCollection", all);
		return "collection/index";
	}
	
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			Collection collection = collectionService.findOne(id).get();
			model.addAttribute("collection", collection);
		}
		return "collection/show";
	}

	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Collection entityCollection) {

		List<User> allUsers = userService.findAll();
		model.addAttribute("users", allUsers);
		List<Book> allBooks = bookService.findAll();
		model.addAttribute("books", allBooks);		
		
		
		return "collection/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Collection entity, BindingResult result, RedirectAttributes redirectAttributes) {
		Collection collection = null;
		try {
			collection = collectionService.save(entity);
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
		
		return "redirect:/collections/" + collection.getId();
	}
	
	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		try {
			if (id != null) {
				List<User> allUsers = userService.findAll();
				model.addAttribute("users", allUsers);
				List<Book> allBooks = bookService.findAll();
				model.addAttribute("books", allBooks);	
				
				Collection entity = collectionService.findOne(id).get();
				model.addAttribute("collection", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "collection/form";
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Collection entity, BindingResult result, RedirectAttributes redirectAttributes) {
		Collection collection = null;
		
		try {
			collection = collectionService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/collections/" + collection.getId();
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				Collection entity = collectionService.findOne(id).get();
				collectionService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/collections/";
	}

}
