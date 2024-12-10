package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.User;

public interface UserService {

	void deleteById(Long id);

	long count();

	Optional<User> findById(Long id);

	List<User> findAll();

	Page<User> findAll(Pageable pageable);

	List<User> findAll(Sort sort);

	<S extends User> S save(S entity);

	Optional<User> findBySlug(String slug);

}