package com.example.repository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.UserBookId;
import com.example.model.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, UserBookId> {
	
}