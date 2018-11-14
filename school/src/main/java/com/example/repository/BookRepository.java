package com.example.repository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	
}