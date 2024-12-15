package vn.iotstar.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;
import vn.iotstar.repository.StoreRepository;
import vn.iotstar.services.StoreService;

@Service
public class StoreServiceImp implements StoreService{
	@Autowired
	private StoreRepository storeRepository;

	@Override
	public <S extends Store> S save(S entity) {
		return storeRepository.save(entity);
	}

	@Override
	public List<Store> findAll(Sort sort) {
		return storeRepository.findAll(sort);
	}

	@Override
	public Page<Store> findAll(Pageable pageable) {
		return storeRepository.findAll(pageable);
	}

	@Override
	public List<Store> findAll() {
		return storeRepository.findAll();
	}

	@Override
	public Optional<Store> findById(Long id) {
		return storeRepository.findById(id);
	}

	@Override
	public long count() {
		return storeRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		storeRepository.deleteById(id);
	}

	@Override
	public Optional<Store> findBySlug(String slug) {
		return storeRepository.findBySlug(slug);
	}



	@Override
	public Page<Store> findByNameContaining(String name, Pageable pageable) {
		return storeRepository.findByNameContaining(name, pageable);
	}
	
}
