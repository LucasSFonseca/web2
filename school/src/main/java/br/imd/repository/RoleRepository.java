package br.imd.repository;
import org.springframework.stereotype.Repository;

import br.imd.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
}