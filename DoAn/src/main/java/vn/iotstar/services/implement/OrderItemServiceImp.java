package vn.iotstar.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.iotstar.entity.OrderItem;
import vn.iotstar.entity.OrderItemId;
import vn.iotstar.repository.OrderItemRepository;
import vn.iotstar.services.OrderItemService;

@Service
@Transactional
public class OrderItemServiceImp implements OrderItemService{
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public <S extends OrderItem> S save(S entity) {
		return orderItemRepository.save(entity);
	}

	@Override
	public List<OrderItem> findAll(Sort sort) {
		return orderItemRepository.findAll(sort);
	}

	@Override
	public Page<OrderItem> findAll(Pageable pageable) {
		return orderItemRepository.findAll(pageable);
	}

	@Override
	public List<OrderItem> findAll() {
		return orderItemRepository.findAll();
	}

	@Override
	public Optional<OrderItem> findById(OrderItemId id) {
		return orderItemRepository.findById(id);
	}

	@Override
	public long count() {
		return orderItemRepository.count();
	}

	@Override
	public void deleteById(OrderItemId id) {
		orderItemRepository.deleteById(id);
	}
	
	
	
}