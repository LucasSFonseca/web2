package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.model.Collection;
import com.example.repository.CollectionRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CollectionService {

	@Autowired
	private CollectionRepository collectionRepository;

	public List<Collection> findAll() {
		return collectionRepository.findAll();
	}

	public Optional<Collection> findOne(Integer id) {
		return collectionRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Collection save(Collection entity) {
		return collectionRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Collection entity) {
		collectionRepository.delete(entity);
	}

}
	
