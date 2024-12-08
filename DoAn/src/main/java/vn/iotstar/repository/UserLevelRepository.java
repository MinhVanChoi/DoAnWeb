package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.UserLevel;

@Repository
public interface UserLevelRepository extends JpaRepository<UserLevel, Long>{

}
