package vn.iotstar.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.services.UserService;
import vn.iotstar.entity.User;


@Controller
public class LoginController {
	
	 @Autowired
	    private UserService userService;
	
	  @GetMapping("/login")
	    public String showRegisterPage() {
		  
	        return "login";  
	    }
	
	  @PostMapping("/login")
		public String handleLogin(@RequestParam(name = "email", required = true) String email,
				@RequestParam(name = "password", required = true) String password) {
			
			if (userService.login(email, password) == false) {
				return "redirect:/login?error!";
			}
			return "redirect:/login?succesfull";
		}
}

