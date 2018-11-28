package br.imd.repository;
import org.springframework.stereotype.Repository;

import br.imd.model.Collection;
import br.imd.model.UserBookId;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, UserBookId> {
	
}