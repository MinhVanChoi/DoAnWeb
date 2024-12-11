package vn.iotstar.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Cart;
import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;
import vn.iotstar.repository.CartRepository;
import vn.iotstar.services.CartService;

@Service
public class CartServiceImp implements CartService{
	@Autowired
	private CartRepository cartRepository;

	@Override
	public <S extends Cart> S save(S entity) {
		return cartRepository.save(entity);
	}

	@Override
	public List<Cart> findAll(Sort sort) {
		return cartRepository.findAll(sort);
	}

	@Override
	public Page<Cart> findAll(Pageable pageable) {
		return cartRepository.findAll(pageable);
	}

	@Override
	public List<Cart> findAll() {
		return cartRepository.findAll();
	}

	@Override
	public Optional<Cart> findById(Long id) {
		return cartRepository.findById(id);
	}

	@Override
	public long count() {
		return cartRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		cartRepository.deleteById(id);
	}

	@Override
	public Optional<Cart> findByUserAndStore(User user, Store store) {
		return cartRepository.findByUserAndStore(user, store);
	}
	
}
