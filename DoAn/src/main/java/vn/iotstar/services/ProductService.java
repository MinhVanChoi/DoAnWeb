package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.Product;

public interface ProductService {

	void deleteById(Long id);

	long count();

	Optional<Product> findById(Long id);

	List<Product> findAll();

	Page<Product> findAll(Pageable pageable);

	List<Product> findAll(Sort sort);

	<S extends Product> S save(S entity);

	List<Product> findBannedProduct();

	List<Product> findUnBannedProduct();

	Optional<Product> findBySlug(String slug);

	Page<Product> findByNameContaining(String name, Pageable pageable);
	
}
