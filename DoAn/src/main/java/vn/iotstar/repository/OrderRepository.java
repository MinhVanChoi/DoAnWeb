package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Order;
import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	List<Order> findByUser(User user);
}
