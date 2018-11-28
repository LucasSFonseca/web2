package br.imd.repository;
import org.springframework.stereotype.Repository;

import br.imd.model.UserBookId;
import br.imd.model.WishList;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, UserBookId> {
	
}