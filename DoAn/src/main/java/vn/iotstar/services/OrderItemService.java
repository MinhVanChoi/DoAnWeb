package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.OrderItem;
import vn.iotstar.entity.OrderItemId;

public interface OrderItemService {

	void deleteById(OrderItemId id);

	long count();

	Optional<OrderItem> findById(OrderItemId id);

	List<OrderItem> findAll();

	Page<OrderItem> findAll(Pageable pageable);

	List<OrderItem> findAll(Sort sort);

	<S extends OrderItem> S save(S entity);

}