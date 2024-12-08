package vn.iotstar.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.iotstar.entity.Store;

public interface StoreRepository extends JpaRepository <Store, Long>   {
	Optional<Store> findByName (String name);
	Page<Store> findByNameContaining (String name, Pageable pageable);
}
