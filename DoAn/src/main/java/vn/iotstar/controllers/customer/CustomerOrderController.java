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
import vn.iotstar.entity.Order;
import vn.iotstar.entity.OrderItem;
import vn.iotstar.entity.OrderItemId;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;
import vn.iotstar.services.CartItemSerice;
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
	CartItemSerice cartItemSerice;
	@RequestMapping("")
	public String all(Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		List<Order> orders = orderService.findByUser(user);
		model.addAttribute("listorder", orders);
		return "user/order";
	}
	
	
	@GetMapping("/product/{slug}")
	public ModelAndView order(ModelMap model, @PathVariable("slug") String slugProduct, HttpSession session) {
		Optional<Product> optProduct = productService.findBySlug(slugProduct);
		User user = (User)session.getAttribute("user");
		if(optProduct.isPresent()) {
			Product product = optProduct.get();
			Store store = product.getStore();
			Order order = new Order();
			order.setUser(user);
			order.setStore(store);
			order.setAddress(user.getAddress());
			order.setPhone(user.getPhone());
			order.setCommission(store.getCommission());
			order.setAmountFormUser(product.getPrice());
			order.setAmountFromStore(product.getPrice()*(0.2 - store.getStorelevel().getDiscount())+store.getCommission().getCost());
			order.setAmountToStore(product.getPrice()*(1-0.2+store.getStorelevel().getDiscount()));
			order.setAmountToGD(product.getPrice()*(0.2 - store.getStorelevel().getDiscount())+store.getCommission().getCost());
			orderService.save(order);
			OrderItem orderItem = new OrderItem();
			OrderItemId id = new OrderItemId(order.getId(), product.getId());
			orderItem.setOrder(order);
			orderItem.setCount(1);
			orderItem.setId(id);
			orderItem.setProduct(product);
			orderItemService.save(orderItem);
		}
		return new ModelAndView("home");
		
	}
	
	
	@PostMapping("/store/{slug}")
	public ModelAndView orderFormStore(ModelMap model, @PathVariable("slug") String slugStore, HttpSession session) {
		Optional<Store> optStore = storeService.findBySlug(slugStore);
		User user = (User)session.getAttribute("user");
		if(optStore.isPresent()) {
			Store store = optStore.get();
			Optional<Cart> optCart = cartService.findByUserAndStore(user, store);
			Order order = new Order();
			order.setUser(user);
			order.setStore(store);
			order.setAddress(user.getAddress());
			order.setCommission(store.getCommission());
			order.setPhone(user.getPhone());
			Cart cart = optCart.get();
			List<OrderItem> orderItems = new ArrayList<>();
			List<CartItem> cartItems = cart.getCartItem();
			for (CartItem cartItem : cartItems) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrder(order);
				orderItem.setProduct(cartItem.getProduct());
				orderItem.setCount(cartItem.getCount());
				orderItems.add(orderItem);
			}
			float amountFormUser = 0;
			float amountFormStore = 0;
			float amountToGD = 0;
			float amountToStore = 0;
			for (OrderItem orderItem : orderItems) {
				amountFormUser += orderItem.getProduct().getPrice()*orderItem.getCount();
				amountFormStore += (orderItem.getProduct().getPrice()*(0.2 - store.getStorelevel().getDiscount())+store.getCommission().getCost())*orderItem.getCount();
				amountToStore += (orderItem.getProduct().getPrice()*(1-0.2+store.getStorelevel().getDiscount()))*orderItem.getCount();
				amountToGD += (orderItem.getProduct().getPrice()*(0.2 - store.getStorelevel().getDiscount())+store.getCommission().getCost())*orderItem.getCount();
			}
			order.setAmountFormUser(amountFormUser);
			order.setAmountFromStore(amountFormStore);
			order.setAmountToGD(amountToGD);
			order.setAmountToStore(amountToStore);
			orderService.save(order);
			for (OrderItem orderItem : orderItems) {
				orderItemService.save(orderItem);
			}
		}
		return new ModelAndView("cart");
	}
}
