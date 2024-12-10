package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.iotstar.entity.UserFollowStore;
import vn.iotstar.entity.UserFollowStoreId;

public interface UserFollowStoreRepository extends JpaRepository<UserFollowStore, UserFollowStoreId>{

}
