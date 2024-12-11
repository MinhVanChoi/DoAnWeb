package vn.iotstar.controllers.customer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vn.iotstar.entity.Order;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;
import vn.iotstar.services.OrderItemService;
import vn.iotstar.services.OrderService;
import vn.iotstar.services.ProductService;
import vn.iotstar.services.StoreService;
import vn.iotstar.services.UserService;

@Controller
@RequestMapping("/order")
public class CustomerOrderController {
//	@Autowired
//	UserService userService;
//	@Autowired
//	OrderService orderService;
//	@Autowired
//	OrderItemService orderItemService;
//	@Autowired
//	ProductService productService;
//	@Autowired
//	StoreService storeService;
//	@PostMapping("{slug}")
//	public ModelAndView order(ModelMap model, @PathVariable("slug") String slugProduct) {
//		Optional<Product> optProduct = productService.findBySlug(slugProduct);
//		if(optProduct.isPresent()) {
//			Product product = optProduct.get();
//			User user = new User();
//			Store store = product.getStore();
//			Order order = new Order();
//			order.setUser(user);
//			order.setStore(store);
//			order.setAddress(user.getAddress());
//			order.setPhone(user.getPhone());
//		}
//	}
	
	
}
