package vn.iotstar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.Store;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	Optional<Product> findBySlug(String slug);
	
	Page<Product> findByNameContaining (String name, Pageable pageable);
	List<Product> findByStore(Store store);
	@Query("SELECT p FROM Product p WHERE p.isSelling = true")
	List<Product> findSellingProduct();
	@Query("SELECT p FROM Product p WHERE p.isSelling = false")
	List<Product> findStoredProduct();
}

