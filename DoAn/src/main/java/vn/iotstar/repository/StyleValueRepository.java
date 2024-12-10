package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.StyleValue;

@Repository
public interface StyleValueRepository extends JpaRepository<StyleValue, Long>{

}