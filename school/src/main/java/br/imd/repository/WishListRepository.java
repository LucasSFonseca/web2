package br.imd.repository;
import org.springframework.stereotype.Repository;

import br.imd.model.UserBookId;
import br.imd.model.WishList;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface WishListRepository extends JpaRepository<WishList, UserBookId> {

	@Query("SELECT p FROM WishList p WHERE p.id.userId = :user_id")
    public List<WishList> findByUser(@Param("user_id") Integer userId);
}