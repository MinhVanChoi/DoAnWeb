package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.Style;

public interface StyleService {

	void deleteById(Long id);

	long count();

	Optional<Style> findById(Long id);

	List<Style> findAll();

	Page<Style> findAll(Pageable pageable);

	List<Style> findAll(Sort sort);

	<S extends Style> S save(S entity);

}
