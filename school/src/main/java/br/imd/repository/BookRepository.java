package br.imd.repository;
import org.springframework.stereotype.Repository;

import br.imd.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	
}