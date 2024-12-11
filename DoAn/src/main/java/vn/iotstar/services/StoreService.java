package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;

public interface StoreService {

	void deleteById(Long id);

	long count();

	Optional<Store> findById(Long id);

	List<Store> findAll();

	Page<Store> findAll(Pageable pageable);

	List<Store> findAll(Sort sort);

	<S extends Store> S save(S entity);

	Optional<Store> findBySlug(String slug);

	List<Store> findByOwner(User user);

	Page<Store> findByNameContaining(String name, Pageable pageable);

}
