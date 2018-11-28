package br.imd.repository;
import org.springframework.stereotype.Repository;

import br.imd.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
}