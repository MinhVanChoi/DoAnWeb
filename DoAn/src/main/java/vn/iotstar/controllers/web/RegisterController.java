package vn.iotstar.controllers.web;

import java.sql.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.services.UserService;
import vn.iotstar.utils.Constain;

@Controller
public class RegisterController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleRepository roleRepository;

	@GetMapping("/register")
	public String showRegisterPage() {
		return "register";
	}

	@PostMapping("/register")
	public String register(@RequestParam(name = "email", required = true) String email,
			@RequestParam(name = "confirmPassword", required = true) String password,
			@RequestParam(name = "fullName", required = true) String fullname,
			@RequestParam(name = "phone", required = true) String phonenumber, Model model) {

		if (userService.checkUserbyEmail(email)) {
			model.addAttribute("error", "Email đã tồn tại. Vui lòng chọn email khác.");
			return "register";
		}
		String slug = Constain.generateSlug(fullname);
		int random = (int)(Math.random()*100);
		Optional<User> optUser = userService.findBySlug(slug);
		if(optUser.isPresent()) {
			slug = slug + "-" + random;
		}
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setPassword(password);
		newUser.setFullname(fullname);
		newUser.setPhone(phonenumber);
		newUser.setSlug(slug);
		newUser.setEmailActive(true);
		newUser.setPhoneActive(true);
		newUser.setBan(false);

		Set<Role> roles = new HashSet<>();
		Role role = roleRepository.findById(1L).orElse(null);
		if (role != null) {
			roles.add(role);
		}
		newUser.setRoles(roles);
		userService.save(newUser);
		return "redirect:/login";

	}
}
