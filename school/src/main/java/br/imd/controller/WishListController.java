package br.imd.controller;

import java.util.HashMap;
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
import br.imd.model.User;
import br.imd.model.UserBookId;
import br.imd.model.WishList;
import br.imd.service.BookService;
import br.imd.service.UserService;
import br.imd.service.WishListService;

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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    User user = userService.findByLogin(name);
		model.addAttribute("user", user);
		
		List<WishList> all = wishListService.findByUser( user.getId() );
		List<Book> allBook = bookService.findByWishList(all);

		HashMap<WishList, Book> mapWishBook = new HashMap<WishList, Book>();
		
		if( !all.isEmpty() )
		{
			for(int i = 0; i < all.size(); i++)
			{
				mapWishBook.put(all.get(i), allBook.get(i));
			}

			model.addAttribute("mapWishBook", mapWishBook);
			//model.addAttribute("listWishList", all);
		}
		
		return "wishList/index";
	}
	
	@GetMapping("/{userId}/{bookId}")
	public String show(Model model, @PathVariable("userId") Integer userId, @PathVariable("bookId") Integer bookId)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    User user = userService.findByLogin(name);
		model.addAttribute("user", user);
		
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

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    User user = userService.findByLogin(name);
		model.addAttribute("user", user);
		
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

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    User user = userService.findByLogin(name);
		model.addAttribute("user", user);
		
		try {
			if (userId != null && bookId != null) 
			{
				UserBookId id = new UserBookId(userId, bookId);

				User entityUser = userService.findOne(userId).get();
				model.addAttribute("users", entityUser);
				Book entityBook = bookService.findOne(bookId).get();
				model.addAttribute("books", entityBook);	
				
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
