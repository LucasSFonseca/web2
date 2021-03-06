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

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import br.imd.api.BookApi;
import br.imd.model.Book;
import br.imd.model.User;
import br.imd.service.BookService;
import br.imd.service.UserService;

@Controller
@RequestMapping("/books")
public class BookController {

	private static final String MSG_SUCESS_INSERT = "Book inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "Book successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted Book successfully.";
	private static final String MSG_ERROR = "Error.";
	private static final String MSG_EXISTS = "Livro j� cadastrado.";

	@Autowired
	private BookService bookService;

	@Autowired
	private UserService userService;

	@GetMapping
	public String index(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    User user = userService.findByLogin(name);
		model.addAttribute("user", user);
		
		List<Book> all = bookService.findAll();
		model.addAttribute("listBook", all);
		return "book/index";
	}
	
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			Book book = bookService.findOne(id).get();
			model.addAttribute("book", book);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    User user = userService.findByLogin(name);
		model.addAttribute("user", user);
		
		return "book/show";
	}

	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Book entityBook) {
		// model.addAttribute("book", entityBook);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    User user = userService.findByLogin(name);
		model.addAttribute("user", user);
		return "book/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Book entity, BindingResult result, RedirectAttributes redirectAttributes) {
		Book book = null;
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
		try {
			Book book2 = BookApi.queryGoogleBooks(jsonFactory, entity.getISBN());
			if(book2 != null) {
				book = bookService.save(book2);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_INSERT);
				return "redirect:/books/" + book.getId();
			}
			else {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("Exception:: exception");
			//e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_EXISTS);
		}catch (Throwable e) {
			System.out.println("Throwable:: exception");
			//e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
		}
		
		return "redirect:/books/";
	}
	
	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    
	    User user = userService.findByLogin(name);
		model.addAttribute("user", user);
		
		try {
			if (id != null) {
				Book entity = bookService.findOne(id).get();
				model.addAttribute("book", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "book/form";
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Book entity, BindingResult result, RedirectAttributes redirectAttributes) {
		Book book = null;
		try {
			book = bookService.update(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/books/" + book.getId();
	}
	
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				Book entity = bookService.findOne(id).get();
				bookService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/books";
	}

}
