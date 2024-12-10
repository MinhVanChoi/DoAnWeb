package vn.iotstar.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.UserLevel;
import vn.iotstar.repository.UserLevelRepository;
import vn.iotstar.services.UserLevelService;

@Service
public class UserlevelServiceImp implements UserLevelService{
	@Autowired
	UserLevelRepository userlevelRepository;

	@Override
	public <S extends UserLevel> S save(S entity) {
		return userlevelRepository.save(entity);
	}

	@Override
	public List<UserLevel> findAll(Sort sort) {
		return userlevelRepository.findAll(sort);
	}

	@Override
	public Page<UserLevel> findAll(Pageable pageable) {
		return userlevelRepository.findAll(pageable);
	}

	@Override
	public List<UserLevel> findAll() {
		return userlevelRepository.findAll();
	}

	@Override
	public Optional<UserLevel> findById(Long id) {
		return userlevelRepository.findById(id);
	}

	@Override
	public long count() {
		return userlevelRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		userlevelRepository.deleteById(id);
	}
	
}
