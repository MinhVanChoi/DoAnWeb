package vn.iotstar.controllers.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.CartItemId;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;
import vn.iotstar.services.CartItemSerice;
import vn.iotstar.services.CartService;
import vn.iotstar.services.ProductService;

@Controller
@RequestMapping("/carts")
public class CustomerCartController {
	@Autowired
	CartService cartService;
	@Autowired
	CartItemSerice cartItemSerice;
	@Autowired
	ProductService productService;
	@GetMapping
	public String viewCart(Model model) {
		User user = new User();
		List<Cart> carts = user.getCarts();
		List<CartItem> cartItems = new ArrayList<>();
		for (Cart cart : carts) {
			List<CartItem> temp = cart.getCartItem();
			cartItems.addAll(temp);
		}
		model.addAttribute("listcart", carts);
		model.addAttribute("listcartItem", cartItems);
		return "user/cart";
	}
	@PostMapping("/add/{slug}")
	public void addProductToCart(@PathVariable("slug") String slugProduct) {
		Optional<Product> optProduct = productService.findBySlug(slugProduct);
		if(optProduct.isPresent()) {
			Product product = optProduct.get();
			Store store = product.getStore();
			User user = new User();
			Optional<Cart> optCart = cartService.findByUserAndStore(user, store);
			if(optCart.isPresent()) {
				Cart cart = optCart.get();
				CartItemId cartItemId = new CartItemId(cart.getId(), product.getId());
				Optional<CartItem> optCartItem = cartItemSerice.findById(cartItemId);
				if(optCartItem.isPresent()) {
					CartItem cartItem = optCartItem.get();
					cartItem.setCount(cartItem.getCount() + 1);
					cartItemSerice.save(cartItem);
				}
				else {
					CartItem cartItem = new CartItem();
					cartItem.setCart(cart);
					cartItem.setProduct(product);
					cartItem.setCount(1);
					cartItemSerice.save(cartItem);
				}
			}
			else {
				Cart cart = new Cart();
				cart.setStore(store);
				cart.setUser(user);
				cartService.save(cart);
				CartItem cartItem = new CartItem();
				cartItem.setCart(cart);
				cartItem.setProduct(product);
				cartItem.setCount(1);
				cartItemSerice.save(cartItem);
			}
		}
	}
	@PostMapping("/edit/{slug}")
	public ModelAndView editCartItem(ModelMap model,@PathVariable("slug") String slugProduct, 
			@Valid @ModelAttribute("cart") Cart cartModel, BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("user/cart");
		}
		Optional<Product> optProduct = productService.findBySlug(slugProduct);
		if(optProduct.isPresent()) {
			Product product = optProduct.get();
			Cart cart = new Cart();
			BeanUtils.copyProperties(cartModel, cart);
			CartItemId cartItemId = new CartItemId(cart.getId(), product.getId());
		    Optional<CartItem> optCartItem = cartItemSerice.findById(cartItemId);
		    if(optCartItem.isPresent()) {
		    	CartItem cartItem = optCartItem.get();
		    	cartItem.setCount(cartItem.getCount() + 1);
		    	cartItemSerice.save(cartItem);
		    }
		}
		return new ModelAndView("foward:/carts", model);
	}
	@PostMapping("/delete/{slug}")
	public ModelAndView deleteCartItem(ModelMap model,@PathVariable("slug") String slugProduct, 
			@Valid @ModelAttribute("cart") Cart cartModel) {
		Optional<Product> optProduct = productService.findBySlug(slugProduct);
		if(optProduct.isPresent()) {
			Product product = optProduct.get();
			Cart cart = new Cart();
			BeanUtils.copyProperties(cartModel, cart);
			CartItemId cartItemId = new CartItemId(cart.getId(), product.getId());
		    Optional<CartItem> optCartItem = cartItemSerice.findById(cartItemId);
		    if(optCartItem.isPresent()) {
		    	cartItemSerice.deleteById(cartItemId);
		    }
		}
		return new ModelAndView("redirect:/carts", model);
	}
}
