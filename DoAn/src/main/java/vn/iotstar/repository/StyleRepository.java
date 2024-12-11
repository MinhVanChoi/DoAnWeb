package vn.iotstar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.iotstar.entity.Style;
import vn.iotstar.entity.User;

public interface StyleRepository extends JpaRepository<Style, Long>{
	Page<Style> findByNameContaining (String name, Pageable pageable);

}
