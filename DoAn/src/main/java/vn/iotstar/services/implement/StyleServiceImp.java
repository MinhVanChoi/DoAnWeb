package vn.iotstar.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Style;
import vn.iotstar.repository.StyleRepository;
import vn.iotstar.services.StyleService;

@Service
public class StyleServiceImp implements StyleService{
	@Autowired
	StyleRepository styleRepository;

	@Override
	public <S extends Style> S save(S entity) {
		return styleRepository.save(entity);
	}

	@Override
	public List<Style> findAll(Sort sort) {
		return styleRepository.findAll(sort);
	}

	@Override
	public Page<Style> findAll(Pageable pageable) {
		return styleRepository.findAll(pageable);
	}

	@Override
	public List<Style> findAll() {
		return styleRepository.findAll();
	}

	@Override
	public Optional<Style> findById(Long id) {
		return styleRepository.findById(id);
	}

	@Override
	public long count() {
		return styleRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		styleRepository.deleteById(id);
	}

	@Override
	public Page<Style> findByNameContaining(String name, Pageable pageable) {
		return styleRepository.findByNameContaining(name, pageable);
	}

	
	
}
