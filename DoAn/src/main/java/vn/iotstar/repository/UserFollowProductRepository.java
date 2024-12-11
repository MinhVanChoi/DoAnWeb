package vn.iotstar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.User;
import vn.iotstar.entity.UserFollowProduct;
import vn.iotstar.entity.UserFollowProductId;

@Repository
public interface UserFollowProductRepository extends JpaRepository<UserFollowProduct, UserFollowProductId>{
	List<UserFollowProduct> findByUser(User user);
}
