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
import com.example.model.User;
import com.example.model.UserBookId;
import com.example.model.WishList;
import com.example.service.BookService;
import com.example.service.UserService;
import com.example.service.WishListService;

@Controller
@RequestMapping("/wishLists")
public class WishListController {

	private static final String MSG_SUCESS_INSERT = "WishList inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "WishList successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted WishList successfully.";
	private static final String MSG_ERROR = "Error.";

	@Autowired
	private WishListService wishListService;
	@Autowired
	private UserService userService; //user service
	@Autowired
	private BookService bookService; //book service
	
	

	@GetMapping
	public String index(Model model) {
		List<WishList> all = wishListService.findAll();
		model.addAttribute("listWishList", all);
		return "wishList/index";
	}
	
	@GetMapping("/{userId}/{bookId}")
	public String show(Model model, @PathVariable("userId") Integer userId, @PathVariable("bookId") Integer bookId)
	{
		if (userId != null && bookId != null) 
		{
			UserBookId id = new UserBookId(userId, bookId);
			WishList wishList = wishListService.findOne(id).get();
			model.addAttribute("wishList", wishList);
		}
		return "wishList/show";
	}

	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute WishList entityWishList) {

		List<User> allUsers = userService.findAll();
		model.addAttribute("users", allUsers);
		List<Book> allBooks = bookService.findAll();
		model.addAttribute("books", allBooks);	
		
		return "wishList/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute WishList entity, BindingResult result, RedirectAttributes redirectAttributes) {
		WishList wishList = null;
		try {
			wishList = wishListService.save(entity);
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
		
		return "redirect:/wishLists/" + wishList.getId().getUserId() + "/" + wishList.getId().getBookId();
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
				
				WishList entity = wishListService.findOne(id).get();
				model.addAttribute("wishList", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "wishList/form";
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute WishList entity, BindingResult result, RedirectAttributes redirectAttributes) {
		WishList wishList = null;
		try {
			wishList = wishListService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/wishLists/" + wishList.getId().getUserId() + "/" + wishList.getId().getBookId();
	}
	
	@RequestMapping("/{userId}/{bookId}/delete")
	public String delete(@PathVariable("userId") Integer userId, @PathVariable("bookId") Integer bookId, RedirectAttributes redirectAttributes) {
		try {
			if (userId != null && bookId != null) 
			{
				UserBookId id = new UserBookId(userId, bookId);
				WishList entity = wishListService.findOne(id).get();
				wishListService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/wishLists";
	}

}
