package vn.iotstar.services.implement;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.services.UserService;

@Service
public class UserServiceImp implements UserService{

	 
	@Autowired
	private UserRepository userRepository;

	 @Autowired
	 private RoleRepository roleRepository;
	 
	@Override
	public <S extends User> S save(S entity) {
		return userRepository.save(entity);
	}

	@Override
	public List<User> findAll(Sort sort) {
		return userRepository.findAll(sort);
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public long count() {
		return userRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public Optional<User> findBySlug(String slug) {
		return userRepository.findBySlug(slug);
	}
	

	 @Override
	public Role createRole(String roleName) {
	        Role role = new Role();
	        role.setRolename(roleName);
	        return roleRepository.save(role);
	    }		
	@Override
	public Boolean checkUserbyEmail(String email) {
		User user = userRepository.findUserByEmail(email);
		if (user == null)
			return false;
		return true;
	}	
	@Override
	public User getUserbyEmail(String email) {
		return userRepository.getUserByEmail(email);
	}

	@Override
	public User login(String email, String password) {
		  User user = userRepository.findUserByEmail(email);
		    if (user == null) {
		        return null; 
		    }
		    if (password.equals(user.getPassword()) && !user.isBan()) {
		        return user; 
		    }
		    return null;
	}

	@Override
	public Page<User> findByFullnameContaining(String name, Pageable pageable) {
		return userRepository.findByFullnameContaining(name, pageable);
	}	
	
	
	
	
}
