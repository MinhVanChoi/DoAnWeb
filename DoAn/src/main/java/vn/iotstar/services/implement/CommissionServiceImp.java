package vn.iotstar.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Commission;
import vn.iotstar.repository.CommissionRepository;
import vn.iotstar.services.CommissionService;

@Service
public class CommissionServiceImp implements CommissionService{
	@Autowired
	CommissionRepository commissionRepository;

	@Override
	public <S extends Commission> S save(S entity) {
		return commissionRepository.save(entity);
	}

	@Override
	public List<Commission> findAll(Sort sort) {
		return commissionRepository.findAll(sort);
	}

	@Override
	public Page<Commission> findAll(Pageable pageable) {
		return commissionRepository.findAll(pageable);
	}

	@Override
	public List<Commission> findAll() {
		return commissionRepository.findAll();
	}

	@Override
	public Optional<Commission> findById(Long id) {
		return commissionRepository.findById(id);
	}

	@Override
	public long count() {
		return commissionRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		commissionRepository.deleteById(id);
	}
	
}
