package vn.iotstar.controllers.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.User;

@Controller
@RequestMapping("/customer")

public class CartController {
	
	  
	  @GetMapping("cart")
		public String viewCart(Model model, HttpSession session) {
			User user = (User)session.getAttribute("user");
			List<Cart> carts = user.getCarts();
			List<CartItem> cartItems = new ArrayList<>();
			for (Cart cart : carts) {
				List<CartItem> temp = cart.getCartItem();
				cartItems.addAll(temp);
			}
			model.addAttribute("listcartItem", cartItems);
			return "customer/usercart";
		}
	
}
