package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.StoreLevel;

public interface StoreLevelService {

	void deleteById(Long id);

	long count();

	Optional<StoreLevel> findById(Long id);

	List<StoreLevel> findAll();

	Page<StoreLevel> findAll(Pageable pageable);

	List<StoreLevel> findAll(Sort sort);

	<S extends StoreLevel> S save(S entity);

}
