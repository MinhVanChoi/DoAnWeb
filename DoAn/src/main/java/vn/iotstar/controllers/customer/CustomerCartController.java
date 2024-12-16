package vn.iotstar.controllers.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.CartItemId;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;
import vn.iotstar.services.CartItemService;
import vn.iotstar.services.CartService;
import vn.iotstar.services.ProductService;
import vn.iotstar.services.UserService;

@Controller
@RequestMapping("/carts")
public class CustomerCartController {
	@Autowired
	CartService cartService;

	@Autowired
	ProductService productService;
	
	@Autowired
	CartItemService cartitemService;
	
	@Autowired
	UserService userService;
	
	
	@RequestMapping("")
	@Transactional
	public String viewCart(Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		Cart cart = user.getCart();
		
		List<CartItem> cartitems = cart.getCartItems();
		List<Product> product_test = new ArrayList<>();
		for(CartItem item : cartitems) {
			Product product_1 = item.getProduct();
			product_test.add(product_1);
		}
		
		double totalAmount = 0;
		for (CartItem item : cartitems) {
		    totalAmount += item.getProduct().getPrice() * item.getCount();
		}

		model.addAttribute("listcaritems",cartitems);
		model.addAttribute("totalAmount", totalAmount); 

		return "cart";
	}
	
	

	// count 1 lay tu view
	@GetMapping("/add/{slug}")
	public ModelAndView addProductToCart(ModelMap model, @PathVariable("slug") String slugProduct, HttpSession session) {
		Optional<Product> optProduct = productService.findBySlug(slugProduct);
		User user = (User)session.getAttribute("user");
		System.out.println(user.getId());
		
		Optional<User> optuser = userService.findBySlug(user.getSlug());
		if(optuser.isPresent()) {
			System.out.println(1234567);
		}
		
		user = optuser.get();
		System.out.println(user.getEmail());
		Product product = optProduct.get();
		System.out.println(product.getName());

		System.out.println();
		if(optProduct.isPresent()) {
			Product product1 = optProduct.get();
			Optional<Cart> optCart = cartService.findByUser(user);
			System.out.println(user.getEmail());
			if(optCart.isPresent()) {
				Cart cart = optCart.get();
				List<CartItem> cartitems = cart.getCartItems();
				List<Product> product_test = new ArrayList<>();
				for(CartItem item : cartitems) {
					Product product_1 = item.getProduct();
					product_test.add(product_1);
					if (product.equals(item.getProduct()))
					{
						CartItemId id_1 = new CartItemId(cart.getId(),product.getId());
						Optional<CartItem> cartitemopt = cartitemService.findById(id_1);
						CartItem cartitemold = cartitemopt.get();
				        int currentCount = cartitemold.getCount();
				        System.out.println("Current count of " + product.getName() + ": " + currentCount);				        System.out.println("Current count of " + product.getName() + ": " + currentCount);
				        System.out.println("Current price of " + product.getPrice() + ": " + currentCount);

				        cartitemold.setCount(currentCount + 1);
				        System.out.println("Updated count: " + cartitemold.getCount());
				        
				        cartitemService.save(cartitemold);
						
					}
					
				}
				if (!product_test.contains(product))
				{
					CartItemId id = new CartItemId(cart.getId(),product.getId());
					CartItem cartitem = new CartItem();
					cartitem.setId(id);
					cartitem.setCart(cart);
					cartitem.setProduct(product);
					cartitem.setCount(1);
					cartitemService.save(cartitem);
				}
				

				}
			else {
				Cart cart = new Cart();
				cart.setUser(user);
				cartService.save(cart);
				
				CartItemId id = new CartItemId(cart.getId(),product.getId());
				CartItem cartitem = new CartItem();
				cartitem.setId(id);
				cartitem.setCart(cart);
				cartitem.setProduct(product);
				cartitem.setCount(1);
				cartitemService.save(cartitem);


			}
			


		}
		
		return new ModelAndView("redirect:/product", model);
	}
	
	
//	
//	@PostMapping("/edit/{slug}")
//	public ModelAndView editCartItem(ModelMap model,@PathVariable("slug") String slugProduct, 
//			@Valid @ModelAttribute("cart") Cart cartModel, BindingResult result) {
//		if(result.hasErrors()) {
//			return new ModelAndView("user/cart");
//		}
//		Optional<Product> optProduct = productService.findBySlug(slugProduct);
//		if(optProduct.isPresent()) {
//			Product product = optProduct.get();
//			Cart cart = new Cart();
//			BeanUtils.copyProperties(cartModel, cart);
//			CartItemId cartItemId = new CartItemId(cart.getId(), product.getId());
//		    Optional<CartItem> optCartItem = cartItemSerice.findById(cartItemId);
//		    if(optCartItem.isPresent()) {
//		    	CartItem cartItem = optCartItem.get();
//		    	cartItem.setCount(cartItem.getCount() + 1);
//		    	cartItemSerice.save(cartItem);
//		    }
//		}
//		return new ModelAndView("foward:/carts", model);
//	}
//	
//	
	@GetMapping("/delete/{slug}")
	public ModelAndView deleteCartItem(ModelMap model,@PathVariable("slug") String slugProduct, 
			@Valid @ModelAttribute("cart") Cart cartModel,  HttpSession session) {
		User user = (User)session.getAttribute("user");
		Cart cart = user.getCart();
		Optional<Product> optProduct = productService.findBySlug(slugProduct);
		if(optProduct.isPresent()) {
			Product product = optProduct.get();
			System.out.println("in ra product: " + product.getId());
			CartItemId cartItemId = new CartItemId(cart.getId(), product.getId());
			
			cartitemService.deleteCartItemsByCartIdAndProductId(cart.getId(),product.getId());
		    
		}
		return new ModelAndView("redirect:/customer/checkout", model);
	}
}
