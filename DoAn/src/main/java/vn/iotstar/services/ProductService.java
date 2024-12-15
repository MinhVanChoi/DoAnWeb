package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.Store;

public interface ProductService {

	void deleteById(Long id);

	long count();

	Optional<Product> findById(Long id);

	List<Product> findAll();

	Page<Product> findAll(Pageable pageable);

	List<Product> findAll(Sort sort);

	<S extends Product> S save(S entity);


	Optional<Product> findBySlug(String slug);

	Page<Product> findByNameContaining(String name, Pageable pageable);

	List<Product> findByStore(Store store);

	List<Product> findStoredProduct();

	List<Product> findSellingProduct();
	
}
