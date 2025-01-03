package vn.iotstar.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findBySlug(String slug);
	User findUserByEmail(String email);
	User getUserByEmail(String email);

	Page<User> findByFullnameContaining (String name, Pageable pageable);

}

