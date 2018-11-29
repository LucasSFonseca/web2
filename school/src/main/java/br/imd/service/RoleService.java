package br.imd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.imd.model.Role;
import br.imd.repository.RoleRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	public Optional<Role> findOne(Integer id) {
		return roleRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Role save(Role entity) {
		return roleRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Role entity) {
		roleRepository.delete(entity);
	}

}
	
