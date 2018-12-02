package br.imd.repository;
import org.springframework.stereotype.Repository;

import br.imd.model.Swap;
import br.imd.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByLogin(String login);
	
	@Query("SELECT p FROM User p WHERE p.id != :user_id")
    public List<User> findByAllExcept(@Param("user_id") Integer userId);
}