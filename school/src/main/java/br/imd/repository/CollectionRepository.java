package br.imd.repository;
import org.springframework.stereotype.Repository;

import br.imd.model.Collection;
import br.imd.model.UserBookId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, UserBookId> {
	
	@Query("SELECT p FROM Collection p WHERE p.id.userId = :user_id")
    public List<Collection> findByUser(@Param("user_id") Integer userId);
}