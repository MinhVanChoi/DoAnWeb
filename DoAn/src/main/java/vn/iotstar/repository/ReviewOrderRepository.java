package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.ReviewOrder;

@Repository
public interface ReviewOrderRepository extends JpaRepository<ReviewOrder, Long>{

}
