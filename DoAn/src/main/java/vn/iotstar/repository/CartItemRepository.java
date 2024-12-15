package vn.iotstar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Cart;
import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.CartItemId;
import vn.iotstar.entity.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    Optional<CartItem> findById(CartItemId cartItemId);
    
    void deleteById(CartItemId cartItemId);
}
