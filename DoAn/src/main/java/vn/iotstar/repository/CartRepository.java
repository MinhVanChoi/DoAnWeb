package vn.iotstar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Cart;
import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	Optional<Cart> findByUserAndStore(User user, Store store);
}
