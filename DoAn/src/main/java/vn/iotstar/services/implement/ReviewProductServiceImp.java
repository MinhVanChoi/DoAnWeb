package vn.iotstar.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.ReviewProduct;
import vn.iotstar.repository.ReviewProductRepository;
import vn.iotstar.services.ReviewProductService;

@Service
public class ReviewProductServiceImp implements ReviewProductService{
	@Autowired
	ReviewProductRepository reviewProductRepository;

	@Override
	public <S extends ReviewProduct> S save(S entity) {
		return reviewProductRepository.save(entity);
	}

	@Override
	public List<ReviewProduct> findAll(Sort sort) {
		return reviewProductRepository.findAll(sort);
	}

	@Override
	public Page<ReviewProduct> findAll(Pageable pageable) {
		return reviewProductRepository.findAll(pageable);
	}

	@Override
	public List<ReviewProduct> findAll() {
		return reviewProductRepository.findAll();
	}

	@Override
	public Optional<ReviewProduct> findById(Long id) {
		return reviewProductRepository.findById(id);
	}

	@Override
	public long count() {
		return reviewProductRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		reviewProductRepository.deleteById(id);
	}
	
}
