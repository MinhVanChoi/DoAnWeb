package vn.iotstar.controllers.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.CartItemId;
import vn.iotstar.entity.Order;
import vn.iotstar.entity.OrderItem;
import vn.iotstar.entity.OrderItemId;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;
import vn.iotstar.services.CartItemService;
import vn.iotstar.services.CartService;
import vn.iotstar.services.OrderItemService;
import vn.iotstar.services.OrderService;
import vn.iotstar.services.ProductService;
import vn.iotstar.services.StoreService;
import vn.iotstar.services.UserService;

@Controller
@RequestMapping("/orders")
public class CustomerOrderController {
	@Autowired
	UserService userService;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	ProductService productService;
	@Autowired
	StoreService storeService;
	@Autowired
	CartService cartService;
	@Autowired
	CartItemService cartItemService;
	@RequestMapping("")
	public String all(Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		List<Order> orders = orderService.findByUser(user);
		model.addAttribute("listorder", orders);
		return "user/order";
	}
	
	
	@GetMapping("/add")
	public ModelAndView order(ModelMap model, HttpSession session) {
		User user = (User)session.getAttribute("user");
	//	double totalamount = (double)session.getAttribute("totalAmount");
		Optional<User> useropt = userService.findBySlug(user.getSlug());
		user = useropt.get();
		System.out.println(user.getId());
			Order order = new Order();
			Cart cart = user.getCart();
			order.setUser(user);
			order.setAddress(user.getAddress());
			order.setPhone(user.getPhone());
			order.setStatus(true);
			orderService.save(order);
			double totalamount = 0; 
			List<CartItem> listcartitem = cart.getCartItems();
			for(CartItem item : listcartitem) {
				Product product = item.getProduct();
				OrderItem orderItem = new OrderItem();
				OrderItemId id = new OrderItemId(order.getId(), product.getId());
				orderItem.setOrder(order);
				orderItem.setCount(item.getCount());
				orderItem.setId(id);
				orderItem.setProduct(product);
				orderItemService.save(orderItem);
				totalamount += product.getPrice()*item.getCount();
				CartItemId cartitemid = new CartItemId(cart.getId(),product.getId());
				System.out.println("Chỗ này có chữ nè: " + cartitemid.getCartId());
				cartItemService.deleteCartItemsByCartId(cartitemid.getCartId());
			}
			order.setTotalAmount(totalamount);
			orderService.save(order);
			
		return new ModelAndView("redirect:/home");
	}
	
	
}
	
	
	

