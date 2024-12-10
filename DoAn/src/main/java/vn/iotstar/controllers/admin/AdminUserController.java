package vn.iotstar.controllers.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vn.iotstar.entity.User;
import vn.iotstar.services.UserService;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
	@Autowired
	UserService userService;
	@RequestMapping("")
	public String all(Model model) {
		List<User> list = userService.findAll();
		model.addAttribute("listuser", list);
		return "/admin/user-list";
	}
	@GetMapping("{slug}")
	public ModelAndView viewUser(ModelMap model, @PathVariable("slug") String slug) {
		Optional<User> optUser = userService.findBySlug(slug);
		if(optUser.isPresent()) {
			User user = optUser.get();
			model.addAttribute("user", user);
			return new ModelAndView("profile-user", model);
		}
		return new ModelAndView("foward:/admin/users", model);
	}
	@PostMapping("/ban/{slug}")
	public ModelAndView banUser(ModelMap model, @PathVariable("slug") String slug) {
		Optional<User> optUser = userService.findBySlug(slug);
		if(optUser.isPresent()) {
			User user = optUser.get();
			user.setBan(true);
			userService.save(user);
			return new ModelAndView("profile-user", model);
		}
		return new ModelAndView("foward:/admin/users", model);
	}
}
