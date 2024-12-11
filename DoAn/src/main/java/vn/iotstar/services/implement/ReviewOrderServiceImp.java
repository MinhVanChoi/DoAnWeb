package vn.iotstar.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.ReviewOrder;
import vn.iotstar.repository.ReviewOrderRepository;
import vn.iotstar.services.ReviewOrderService;

@Service
public class ReviewOrderServiceImp implements ReviewOrderService{
	@Autowired
	ReviewOrderRepository reviewOrderRepository;

	@Override
	public <S extends ReviewOrder> S save(S entity) {
		return reviewOrderRepository.save(entity);
	}

	@Override
	public List<ReviewOrder> findAll(Sort sort) {
		return reviewOrderRepository.findAll(sort);
	}

	@Override
	public Page<ReviewOrder> findAll(Pageable pageable) {
		return reviewOrderRepository.findAll(pageable);
	}

	@Override
	public List<ReviewOrder> findAll() {
		return reviewOrderRepository.findAll();
	}

	@Override
	public Optional<ReviewOrder> findById(Long id) {
		return reviewOrderRepository.findById(id);
	}

	@Override
	public long count() {
		return reviewOrderRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		reviewOrderRepository.deleteById(id);
	}
	
	
}
