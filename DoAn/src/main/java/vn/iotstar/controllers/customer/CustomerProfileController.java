package vn.iotstar.controllers.customer;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.iotstar.entity.User;
import vn.iotstar.services.UserService;

@Controller
@RequestMapping("/profile")
public class CustomerProfileController {
	@Autowired
	UserService userService;
	@RequestMapping("")
	public String profile(Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		model.addAttribute("user", user);
		return "user-profile";
	}
	@PostMapping("/update")
	public ModelAndView editProfile(ModelMap model, @Valid @ModelAttribute("user") User userModel, BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("user/profile");
		}
		User user = new User();
		BeanUtils.copyProperties(userModel, user);
		Optional<User> optuser= userService.findById(user.getId());
		User userold=optuser.get();
		user.setPassword(userold.getPassword());
		userService.save(user);
		return new ModelAndView("redirect:/home", model);
	}
}
