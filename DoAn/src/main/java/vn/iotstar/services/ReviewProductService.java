package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.ReviewProduct;

public interface ReviewProductService {

	void deleteById(Long id);

	long count();

	Optional<ReviewProduct> findById(Long id);

	List<ReviewProduct> findAll();

	Page<ReviewProduct> findAll(Pageable pageable);

	List<ReviewProduct> findAll(Sort sort);

	<S extends ReviewProduct> S save(S entity);

}
