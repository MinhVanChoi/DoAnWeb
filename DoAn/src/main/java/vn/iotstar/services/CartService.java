package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.Cart;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;

public interface CartService {

	void deleteById(Long id);

	long count();

	Optional<Cart> findById(Long id);

	List<Cart> findAll();

	Page<Cart> findAll(Pageable pageable);

	List<Cart> findAll(Sort sort);

	<S extends Cart> S save(S entity);


	Optional<Cart> findByUser(User user);

}
