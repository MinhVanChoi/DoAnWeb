package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.UserFollowStore;
import vn.iotstar.entity.UserFollowStoreId;

public interface UserFollowStoreService {

	void deleteById(UserFollowStoreId id);

	long count();

	Optional<UserFollowStore> findById(UserFollowStoreId id);

	List<UserFollowStore> findAll();

	Page<UserFollowStore> findAll(Pageable pageable);

	List<UserFollowStore> findAll(Sort sort);

	<S extends UserFollowStore> S save(S entity);

}
