package com.example.repository;
import org.springframework.stereotype.Repository;

import com.example.model.Swap;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SwapRepository extends JpaRepository<Swap, Integer> {
	
}