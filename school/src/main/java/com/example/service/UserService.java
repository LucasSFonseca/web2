package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public Optional<User> findOne(Integer id) {
		return userRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public User save(User entity) {
		return userRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(User entity) {
		userRepository.delete(entity);
	}

}
	
