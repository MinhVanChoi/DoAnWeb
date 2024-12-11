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

import jakarta.servlet.http.HttpSession;
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
	@RequestMapping("")
	public String followedProduct(Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		List<UserFollowProduct> list = userFollowProductService.findByUser(user);
		model.addAttribute("listuserfollowproduct", list);
		return "user/follow-product";
	}
	@GetMapping("/follow/{slug}")
	public ModelAndView followProduct(@PathVariable("slug") String slugProduct, HttpSession session) {
		User user = (User)session.getAttribute("user");
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
		//sua lai tra ve trang dang xem
		return new ModelAndView("redirect:/follow-products");
	}
	@GetMapping("unfollow/{slug}")
	public ModelAndView unFollowProduct(@PathVariable("slug") String slugProduct, HttpSession session) {
		User user = (User)session.getAttribute("user");
		Optional<Product> optProduct = productService.findBySlug(slugProduct);
		if(optProduct.isPresent()) {
			Product product = optProduct.get();
			UserFollowProductId userFollowProductId = new UserFollowProductId(user.getId(), product.getId());
			Optional<UserFollowProduct> optUserFollowProduct = userFollowProductService.findById(userFollowProductId);
			if(optUserFollowProduct.isPresent()) {
				userFollowProductService.deleteById(userFollowProductId);
			}
		}
		//sua lai tra ve trang dang xem
		return new ModelAndView("redirect:/follow-products");
	}
}
