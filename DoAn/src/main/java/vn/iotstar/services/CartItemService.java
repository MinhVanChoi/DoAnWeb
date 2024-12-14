package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.CartItemId;

public interface CartItemService {

	void deleteById(CartItemId id);

	long count();

	Optional<CartItem> findById(CartItemId id);

	List<CartItem> findAll();

	Page<CartItem> findAll(Pageable pageable);

	List<CartItem> findAll(Sort sort);

	<S extends CartItem> S save(S entity);

}
