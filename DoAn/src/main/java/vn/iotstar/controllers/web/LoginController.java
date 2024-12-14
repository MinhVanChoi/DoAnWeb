package vn.iotstar.controllers.web;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.services.UserService;
import vn.iotstar.entity.Role;
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
				@RequestParam(name = "password", required = true) String password,
				HttpSession session) {
		  User user = userService.login(email, password);
			
			if (user == null) {
				return "redirect:/login?error=true";
			}
			if ("admin@gmail.com".equals(email)) {
				session.setAttribute("user", user); 
				return "redirect:/admin/home"; }
			session.setAttribute("user", user); 

			return "redirect:/home"; 
		}
	  
	  @GetMapping("/logout")
	    public String handleLogout(HttpSession session) {
	        session.invalidate();
	        return "redirect:/login"; 
	    }
}

