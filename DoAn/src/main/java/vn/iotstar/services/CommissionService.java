package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.Commission;

public interface CommissionService {

	void deleteById(Long id);

	long count();

	Optional<Commission> findById(Long id);

	List<Commission> findAll();

	Page<Commission> findAll(Pageable pageable);

	List<Commission> findAll(Sort sort);

	<S extends Commission> S save(S entity);

}
