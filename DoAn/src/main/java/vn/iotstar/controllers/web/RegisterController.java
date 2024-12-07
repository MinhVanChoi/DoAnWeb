package vn.iotstar.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class RegisterController {

	   @GetMapping("/register")
	    public String showRegisterPage() {
	        return "register";  
	    }
}

