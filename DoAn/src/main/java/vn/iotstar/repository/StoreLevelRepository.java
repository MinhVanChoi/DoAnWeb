package vn.iotstar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.StoreLevel;

@Repository
public interface StoreLevelRepository extends JpaRepository<StoreLevel, Long>{
	Page<StoreLevel> findByNameContaining (String name, Pageable pageable);

}
