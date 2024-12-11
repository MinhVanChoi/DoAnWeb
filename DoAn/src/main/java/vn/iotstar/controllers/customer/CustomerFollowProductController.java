package vn.iotstar.controllers.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.User;
import vn.iotstar.entity.UserFollowProduct;
import vn.iotstar.entity.UserFollowProductId;
import vn.iotstar.services.ProductService;
import vn.iotstar.services.UserFollowProductService;

@Controller
@RequestMapping("/follow-products")
public class CustomerFollowProductController {
	@Autowired
	UserFollowProductService userFollowProductService;
	@Autowired
	ProductService productService;
	@GetMapping
	public String followedProduct(Model model) {
		User user = new User();
		List<UserFollowProduct> list = userFollowProductService.findByUser(user);
		model.addAttribute("listuserfollowproduct", list);
		return "user/follow-product";
	}
	@PostMapping("/follow/{slug}")
	public void followProduct(@PathVariable("slug") String slugProduct) {
		User user = new User();
		Optional<Product> optProduct = productService.findBySlug(slugProduct);
		if(optProduct.isPresent()) {
			Product product = optProduct.get();
			UserFollowProductId userFollowProductId = new UserFollowProductId(user.getId(), product.getId());
			UserFollowProduct userFollowProduct = new UserFollowProduct();
			userFollowProduct.setId(userFollowProductId);
			userFollowProduct.setUser(user);
			userFollowProduct.setProduct(product);
			userFollowProductService.save(userFollowProduct);
		}
	}
	@PostMapping("unfollow/{slug}")
	public ModelAndView unFollowProduct(@PathVariable("slug") String slugProduct) {
		User user = new User();
		Optional<Product> optProduct = productService.findBySlug(slugProduct);
		if(optProduct.isPresent()) {
			Product product = optProduct.get();
			UserFollowProductId userFollowProductId = new UserFollowProductId(user.getId(), product.getId());
			Optional<UserFollowProduct> optUserFollowProduct = userFollowProductService.findById(userFollowProductId);
			if(optUserFollowProduct.isPresent()) {
				UserFollowProduct userFollowProduct = optUserFollowProduct.get();
				userFollowProductService.deleteById(userFollowProductId);
			}
		}
		return new ModelAndView("redirect:/follow-products");
	}
}
