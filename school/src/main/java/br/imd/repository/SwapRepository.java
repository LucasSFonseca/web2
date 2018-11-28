package br.imd.repository;
import org.springframework.stereotype.Repository;

import br.imd.model.Swap;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SwapRepository extends JpaRepository<Swap, Integer> {
	
}