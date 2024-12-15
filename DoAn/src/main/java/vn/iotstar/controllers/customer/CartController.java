package vn.iotstar.controllers.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.User;

@Controller
@RequestMapping("/customer")

public class CartController {
	
	  
	  @GetMapping("checkout")
		public String viewCart(Model model, HttpSession session) {
			
			return "checkout";
		}
	
}
