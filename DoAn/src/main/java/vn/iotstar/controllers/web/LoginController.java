package vn.iotstar.controllers.web;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.services.UserService;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;


@Controller
public class LoginController {
	
	 @Autowired
	    private UserService userService;
	
	  @GetMapping("/login")
	    public String ShowLogin() {
		  
	        return "login";  
	    }
	  
	  @GetMapping("/logout")
	  public String logout(HttpSession session) {
	      session.invalidate(); 
	      return "redirect:/login"; 
	  }
	
	  @PostMapping("/login")
	  public String handleLogin(@RequestParam(name = "email", required = true) String email,
	                            @RequestParam(name = "password", required = true) String password,
	                            @RequestParam(name = "rememberMe", required = false) boolean rememberMe,
	                            HttpServletResponse response,
	                            HttpSession session) {

	      User user = userService.login(email, password);
	      if (user == null) {
	          return "redirect:/login?error=true";
	      }
	      
	      if (user.getRole() == 1) {
	          session.setAttribute("user", user); 
	          return "redirect:/admin/home";
	      }
	      
	      session.setAttribute("user", user);
	      if (rememberMe) {
	          saveRememberMe(response, email);
	      }
	      return "redirect:/home"; 
	  }
	  
	    @GetMapping("/remember-me")
	    public ResponseEntity<String> saveRememberMe(HttpServletResponse response, @RequestParam String username) {
	        Cookie cookie = new Cookie("COOKIE_REMEMBER", username);
	        cookie.setMaxAge(30 * 60);  
	        cookie.setHttpOnly(true);   
	        cookie.setPath("/");       

	        response.addCookie(cookie);
System.out.println("Cookie 'Remember Me' đã được lưu.");
	        return ResponseEntity.ok("Cookie 'Remember Me' đã được lưu.");
	    }
	    
	    
	    
}

