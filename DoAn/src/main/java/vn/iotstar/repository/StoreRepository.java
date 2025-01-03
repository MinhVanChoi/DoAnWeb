package vn.iotstar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;

@Repository
public interface StoreRepository extends JpaRepository <Store, Long>   {
	Optional<Store> findByName (String name);
	Page<Store> findByNameContaining (String name, Pageable pageable);
	Optional<Store> findBySlug(String slug);
	
}
