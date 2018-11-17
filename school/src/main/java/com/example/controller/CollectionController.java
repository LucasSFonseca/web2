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
import com.example.model.UserBookId;
import com.example.service.BookService;
import com.example.service.CollectionService;
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
	
	@GetMapping("/{userId}/{bookId}")
	public String show(Model model, @PathVariable("userId") Integer userId, @PathVariable("bookId") Integer bookId)
	{
		if (userId != null && bookId != null) 
		{
			UserBookId id = new UserBookId(userId, bookId);
			
			Collection collection = collectionService.findOne(id).get();
			model.addAttribute("collection", collection);
		}
		return "collection/show";
	}

	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Collection entityCollection) {
		
		//model.addAttribute("collection", entityCollection);
		List<User> allUsers = userService.findAll();
		model.addAttribute("users", allUsers);
		List<Book> allBooks = bookService.findAll();
		model.addAttribute("books", allBooks);		
		
		
		return "collection/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Collection entityCollection, BindingResult result, RedirectAttributes redirectAttributes) {
		Collection collection = null;
		try {
			collection = collectionService.save(entityCollection);
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
		
		return "redirect:/collections/" + collection.getId().getUserId() + "/" + collection.getId().getBookId();
	}
	
	@GetMapping("/{userId}/{bookId}/edit")
	public String update(Model model, @PathVariable("userId") Integer userId, @PathVariable("bookId") Integer bookId) {
		try {
			if (userId != null && bookId != null) 
			{
				UserBookId id = new UserBookId(userId, bookId);
				
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
		return "redirect:/collections/" + collection.getId().getUserId() + "/" + collection.getId().getBookId();
	}
	
	@RequestMapping("/{userId}/{bookId}/delete")
	public String delete(@PathVariable("userId") Integer userId, @PathVariable("bookId") Integer bookId, RedirectAttributes redirectAttributes) {
		try {
			if (userId != null && bookId != null) 
			{
				UserBookId id = new UserBookId(userId, bookId);
				Collection entity = collectionService.findOne(id).get();
				collectionService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/collections";
	}

}
