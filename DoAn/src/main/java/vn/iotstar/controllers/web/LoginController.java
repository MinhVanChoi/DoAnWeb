package vn.iotstar.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.services.UserService;

@Controller
public class LoginController {
	  @Autowired
	    private UserService userService;

	  @GetMapping("/login")
	    public String showRegisterPage() {
	        return "login";  
	    }
	
	  
	  @PostMapping("/login")
	  public String handleLogin(@RequestParam String email, @RequestParam String password, Model model) {
		    if (email != null && password != null) {
		        User user = userService.login(email, password);
		        System.out.println("Email: " + email);  
		        System.out.println("Password: " + password); 

		        if (user != null) {
		            return "redirect:/home"; 
		        } else {
		            model.addAttribute("error", "Invalid email or password."); 
		            return "login"; 
		        }
		    } else {
		        model.addAttribute("error", "Email and password must not be empty."); 
		        return "login";  
		    }
		}
	  
	  
	  
	  
}

