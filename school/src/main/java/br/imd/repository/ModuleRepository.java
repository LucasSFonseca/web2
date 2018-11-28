package br.imd.repository;
import org.springframework.stereotype.Repository;

import br.imd.model.Module;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {
	
}