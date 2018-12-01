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
import br.imd.model.Collection;
import br.imd.model.User;
import br.imd.model.UserBookId;
import br.imd.service.BookService;
import br.imd.service.CollectionService;
import br.imd.service.UserService;

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

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    User user = userService.findByLogin(name);
		model.addAttribute("user", user);
		
		/*List<Collection> all = collectionService.findByUser( user.getId() );
		
		if( !all.isEmpty() )
			model.addAttribute("listCollection", all);*/
		
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
				
				//model.addAttribute("collection", entityCollection);
				User entityUser = userService.findOne(userId).get();
				model.addAttribute("users", entityUser);
			    Book entityBook = bookService.findOne(bookId).get();
				model.addAttribute("books", entityBook);		
				
				Collection entity = collectionService.findOne(id).get();
				model.addAttribute("collection", entity);

				System.out.println("Collection User: " + entity.getUser() );
				System.out.println("Collection Book: " + entity.getBook() );
				System.out.println("Collection Id: " + entity.getId() );
				System.out.println("Collection UserId: " + entity.getId().getUserId());
				System.out.println("Collection BookId: " + entity.getId().getBookId());
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
