package vn.iotstar.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.Store;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.services.ProductService;

@Service
@Transactional
public class ProductServiceImp implements ProductService{
	@Autowired
	private ProductRepository productRepository;

	@Override
	public <S extends Product> S save(S entity) {
		return productRepository.save(entity);
	}

	@Override
	public List<Product> findAll(Sort sort) {
		return productRepository.findAll(sort);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public long count() {
		return productRepository.count();
	}

	@Override
	public Optional<Product> findBySlug(String slug) {
		return productRepository.findBySlug(slug);
	}

	@Override
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}



	@Override
	public Page<Product> findByNameContaining(String name, Pageable pageable) {
		return productRepository.findByNameContaining(name, pageable);
	}


	@Override
	public List<Product> findSellingProduct() {
		return productRepository.findSellingProduct();
	}

	@Override
	public List<Product> findStoredProduct() {
		return productRepository.findStoredProduct();
	}
	
	
}
