package vn.iotstar.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	  @GetMapping("/login")
	    public String showRegisterPage() {
	        return "login";  
	    }
}

