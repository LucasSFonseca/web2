package br.imd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.imd.model.Book;
import br.imd.model.Collection;
import br.imd.model.WishList;
import br.imd.repository.BookRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public List<Book> findAll() {
		return bookRepository.findAll();
	}
	
	public List<Book> findByCollections(List<Collection> collections)
	{
		List<Book> books = new ArrayList<Book>();
		
		for( Collection col : collections )
		{
			books.add( bookRepository.findById( col.getId().getBookId() ).get() );
		}
		
		return books;
	}
	
	public List<Book> findByWishList(List<WishList> wishlist)
	{
		List<Book> books = new ArrayList<Book>();
		
		for( WishList col : wishlist )
		{
			books.add( bookRepository.findById( col.getId().getBookId() ).get() );
		}
		
		return books;
	}

	public Optional<Book> findOne(Integer id) {
		return bookRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Book save(Book entity) {
		return bookRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Book entity) {
		bookRepository.delete(entity);
	}

}
	
