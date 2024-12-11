package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.Order;
import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;

public interface OrderService {

	void deleteById(Long id);

	long count();

	Optional<Order> findById(Long id);

	List<Order> findAll();

	Page<Order> findAll(Pageable pageable);

	List<Order> findAll(Sort sort);

	<S extends Order> S save(S entity);

	List<Order> findByStore(Store store);

	List<Order> findByUser(User user);

}
