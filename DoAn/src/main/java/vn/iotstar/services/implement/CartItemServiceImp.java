package vn.iotstar.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.CartItemId;
import vn.iotstar.repository.CartItemRepository;
import vn.iotstar.services.CartItemService;

@Service
public class CartItemServiceImp implements CartItemService{
	@Autowired
	CartItemRepository cartItemRepository;

	@Override
	public <S extends CartItem> S save(S entity) {
		return cartItemRepository.save(entity);
	}

	@Override
	public List<CartItem> findAll(Sort sort) {
		return cartItemRepository.findAll(sort);
	}

	@Override
	public Page<CartItem> findAll(Pageable pageable) {
		return cartItemRepository.findAll(pageable);
	}

	@Override
	public List<CartItem> findAll() {
		return cartItemRepository.findAll();
	}

	@Override
	public Optional<CartItem> findById(CartItemId id) {
		return cartItemRepository.findById(id);
	}

	@Override
	public long count() {
		return cartItemRepository.count();
	}

	@Override
	public void deleteById(CartItemId id) {
		cartItemRepository.deleteById(id);
	}
	
}
