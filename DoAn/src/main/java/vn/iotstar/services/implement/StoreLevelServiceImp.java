package vn.iotstar.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.StoreLevel;
import vn.iotstar.repository.StoreLevelRepository;
import vn.iotstar.services.StoreLevelService;

@Service
public class StoreLevelServiceImp implements StoreLevelService{
	@Autowired
	StoreLevelRepository storeLevelRepository;

	@Override
	public <S extends StoreLevel> S save(S entity) {
		return storeLevelRepository.save(entity);
	}

	@Override
	public List<StoreLevel> findAll(Sort sort) {
		return storeLevelRepository.findAll(sort);
	}

	@Override
	public Page<StoreLevel> findAll(Pageable pageable) {
		return storeLevelRepository.findAll(pageable);
	}

	@Override
	public List<StoreLevel> findAll() {
		return storeLevelRepository.findAll();
	}

	@Override
	public Optional<StoreLevel> findById(Long id) {
		return storeLevelRepository.findById(id);
	}

	@Override
	public long count() {
		return storeLevelRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		storeLevelRepository.deleteById(id);
	}
	
}
