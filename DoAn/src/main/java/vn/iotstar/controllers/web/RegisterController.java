package vn.iotstar.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.services.UserService;


@Controller
public class RegisterController {
	 @Autowired
	    private UserService userService;
	 
	   @GetMapping("/register")
	    public String showRegisterPage() {
	        return "register";  
	    }
	   
	   @PostMapping("/register")
		public String register(@RequestParam(name = "email", required = true) String email,
				@RequestParam(name = "password", required = true) String password) {
			
			if (userService.login(email, password) == false) {
				return "redirect:/login?error!";
			}
			return "redirect:/login?succesfull";
		}
}

