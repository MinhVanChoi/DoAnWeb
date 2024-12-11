package vn.iotstar.services.implement;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.services.UserService;
import vn.iotstar.configs.SecurityConfig;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserServiceImp implements UserService{

	

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	 
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
	

	 public Role createRole(String roleName) {
	        Role role = new Role();
	        role.setRolename(roleName);
	        return roleRepository.save(role);
	    }
	 
		public User createUser() {
			User user = new User();
			user.setEmail("4@gmail.com");
			user.setPhone("1234");
			user.setId_card("1234");
			user.setFullname("Nguyen Van D");
			user.setAddress("123 Example Street");
			user.setSlug("nguyen-van-d");
			String password = "password123";			    
			String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
			user.setPassword(encodedPassword); // Gán mật khẩu đã mã hóa
			user.setAvatar("avatar.jpg");
			user.setEmailActive(true);
			user.setPhoneActive(true);
			user.setBan(false);
			user.setCreateAt(new Date(System.currentTimeMillis()));
			user.setUpdateAt(new Date(System.currentTimeMillis()));

		    Set<Role> roles = new HashSet<>();
		    Role role = roleRepository.findById(1L).orElse(null);
		    if (role != null) {
		        roles.add(role);
		    }
		    user.setRoles(roles);  

		    return userRepository.save(user);
		}
		
		
		public String MaHoaMatKhau(String mk) {
	return BCrypt.hashpw(mk, BCrypt.gensalt());
}
		
		
		   public String chuyenthanhSlug(String slug) {
		        if (slug == null || slug.isEmpty()) {
		            return "";
		        }
		        String result = slug.toLowerCase()
		                            .trim()
		                            .replaceAll("[^a-z0-9\\s-]", "")
		                            .replaceAll("[\\s_-]+", "-") 
		                            .replaceAll("^-+|-+$", ""); 
		        return result;
		    }

		
	public Boolean checkUserbyEmail(String email) {
		User user = userRepository.findUserByEmail(email);
		if (user == null)
			return false;
		return true;
	}
	
	public Boolean checkPasswordUser(String email, String password)
	{
		User user = userRepository.findUserByEmail(email);
		 if (user == null) {
	            return false;
	        }
		 return passwordEncoder.matches(password, user.getPassword());
		 
	}
	
	public User getUserbyEmail(String email) {
		return userRepository.getUserByEmail(email);
	}

	@Override
	public User login(String email, String password) {
		  User user = userRepository.findUserByEmail(email);
		    if (user == null) {
		        return null; 
		    }
		    if (passwordEncoder.matches(password, user.getPassword())) {
		        return user; 
		    }

		    return null;
	}
	
		


		
	
	
}
