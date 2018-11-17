package com.example.repository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Collection;
import com.example.model.UserBookId;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, UserBookId> {
	
}