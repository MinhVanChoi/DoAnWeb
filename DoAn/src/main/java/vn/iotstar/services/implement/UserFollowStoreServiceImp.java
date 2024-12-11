package vn.iotstar.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.UserFollowStore;
import vn.iotstar.entity.UserFollowStoreId;
import vn.iotstar.repository.UserFollowStoreRepository;
import vn.iotstar.services.UserFollowStoreService;

@Service
public class UserFollowStoreServiceImp implements UserFollowStoreService{
	@Autowired
	UserFollowStoreRepository userFollowStoreRepository;

	@Override
	public <S extends UserFollowStore> S save(S entity) {
		return userFollowStoreRepository.save(entity);
	}

	@Override
	public List<UserFollowStore> findAll(Sort sort) {
		return userFollowStoreRepository.findAll(sort);
	}

	@Override
	public Page<UserFollowStore> findAll(Pageable pageable) {
		return userFollowStoreRepository.findAll(pageable);
	}

	@Override
	public List<UserFollowStore> findAll() {
		return userFollowStoreRepository.findAll();
	}

	@Override
	public Optional<UserFollowStore> findById(UserFollowStoreId id) {
		return userFollowStoreRepository.findById(id);
	}

	@Override
	public long count() {
		return userFollowStoreRepository.count();
	}

	@Override
	public void deleteById(UserFollowStoreId id) {
		userFollowStoreRepository.deleteById(id);
	}
	
}
