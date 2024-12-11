package vn.iotstar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.entity.User;
import vn.iotstar.entity.UserFollowProduct;
import vn.iotstar.entity.UserFollowProductId;

public interface UserFollowProductService {

	void deleteById(UserFollowProductId id);

	long count();

	Optional<UserFollowProduct> findById(UserFollowProductId id);

	List<UserFollowProduct> findAll();

	Page<UserFollowProduct> findAll(Pageable pageable);

	List<UserFollowProduct> findAll(Sort sort);

	<S extends UserFollowProduct> S save(S entity);

	List<UserFollowProduct> findByUser(User user);

}
