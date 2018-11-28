package br.imd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.imd.model.UserBookId;
import br.imd.model.WishList;
import br.imd.repository.WishListRepository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class WishListService {

	@Autowired
	private WishListRepository wishListRepository;

	public List<WishList> findAll() {
		return wishListRepository.findAll();
	}

	public Optional<WishList> findOne(UserBookId id) {
		return wishListRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public WishList save(WishList entity) {
		return wishListRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(WishList entity) {
		wishListRepository.delete(entity);
	}

}
	
