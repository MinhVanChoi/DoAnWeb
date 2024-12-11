package vn.iotstar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Commission;
import vn.iotstar.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	Optional<Product> findBySlug(String slug);
	@Query("SELECT p FROM Product p WHERE p.isBan = false")
	List<Product> findUnBannedProduct();
	@Query("SELECT p FROM Product p WHERE p.isBan = true")
	List<Product> findBannedProduct();
	Page<Product> findByNameContaining (String name, Pageable pageable);

}

