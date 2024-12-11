package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.ReviewOrder;

public interface ReviewOrderService {

	void deleteById(Long id);

	long count();

	Optional<ReviewOrder> findById(Long id);

	List<ReviewOrder> findAll();

	Page<ReviewOrder> findAll(Pageable pageable);

	List<ReviewOrder> findAll(Sort sort);

	<S extends ReviewOrder> S save(S entity);

}
