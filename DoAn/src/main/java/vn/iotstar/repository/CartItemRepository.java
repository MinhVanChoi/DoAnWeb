package vn.iotstar.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.CartItemId;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId>{
}
