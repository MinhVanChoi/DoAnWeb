package vn.iotstar.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Commission;

@Repository
public interface CommissionRepository  extends JpaRepository <Commission, Long>  {
	Optional<Commission> findByName (String name);
	Page<Commission> findByNameContaining (String name, Pageable pageable);
	
}
