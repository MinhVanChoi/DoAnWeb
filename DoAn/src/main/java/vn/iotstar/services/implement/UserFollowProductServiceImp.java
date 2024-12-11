package vn.iotstar.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.User;
import vn.iotstar.entity.UserFollowProduct;
import vn.iotstar.entity.UserFollowProductId;
import vn.iotstar.repository.UserFollowProductRepository;
import vn.iotstar.services.UserFollowProductService;

@Service
public class UserFollowProductServiceImp implements UserFollowProductService{
	@Autowired
	UserFollowProductRepository userFollowProductRepository;

	@Override
	public <S extends UserFollowProduct> S save(S entity) {
		return userFollowProductRepository.save(entity);
	}

	@Override
	public List<UserFollowProduct> findAll(Sort sort) {
		return userFollowProductRepository.findAll(sort);
	}

	@Override
	public Page<UserFollowProduct> findAll(Pageable pageable) {
		return userFollowProductRepository.findAll(pageable);
	}

	@Override
	public List<UserFollowProduct> findAll() {
		return userFollowProductRepository.findAll();
	}

	@Override
	public Optional<UserFollowProduct> findById(UserFollowProductId id) {
		return userFollowProductRepository.findById(id);
	}

	@Override
	public long count() {
		return userFollowProductRepository.count();
	}

	@Override
	public void deleteById(UserFollowProductId id) {
		userFollowProductRepository.deleteById(id);
	}

	@Override
	public List<UserFollowProduct> findByUser(User user) {
		return userFollowProductRepository.findByUser(user);
	}
	
}
