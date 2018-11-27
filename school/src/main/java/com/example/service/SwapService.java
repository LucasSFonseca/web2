package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.model.Swap;
import com.example.repository.SwapRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SwapService
{

	@Autowired
	private SwapRepository swapRepository;

	public List<Swap> findAll() {
		return swapRepository.findAll();
	}

	public Optional<Swap> findOne(Integer id) {
		return swapRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Swap save(Swap entity) {
		return swapRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Swap entity) {
		swapRepository.delete(entity);
	}

}
	
