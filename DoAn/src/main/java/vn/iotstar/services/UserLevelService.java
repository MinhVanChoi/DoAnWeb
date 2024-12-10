package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.UserLevel;

public interface UserLevelService {

	void deleteById(Long id);

	long count();

	Optional<UserLevel> findById(Long id);

	List<UserLevel> findAll();

	Page<UserLevel> findAll(Pageable pageable);

	List<UserLevel> findAll(Sort sort);

	<S extends UserLevel> S save(S entity);

}
