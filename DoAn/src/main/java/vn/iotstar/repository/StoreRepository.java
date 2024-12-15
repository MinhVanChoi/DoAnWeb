package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iotstar.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
}