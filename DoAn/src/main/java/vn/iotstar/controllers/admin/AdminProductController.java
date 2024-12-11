package vn.iotstar.controllers.admin;

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

import vn.iotstar.entity.Product;
import vn.iotstar.services.ProductService;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
	@Autowired
	ProductService productService;
	
	@RequestMapping("")
	public String all(Model model) {
		List<Product> list = productService.findAll();
		model.addAttribute("listproduct", list);
		return "admin/product-list";
	}
	@GetMapping("/banned-product")
	public String bannedProduct(Model model) {
		List<Product> list = productService.findBannedProduct();
		model.addAttribute("listproduct", list);
		return "admin/product-list";
	}
	@GetMapping("/unbanned-product")
	public String unBannedProduct(Model model) {
		List<Product> list = productService.findUnBannedProduct();
		model.addAttribute("listproduct", list);
		return "admin/product-list";
	}
	@GetMapping("/{slug}")
	public ModelAndView viewProduct(ModelMap model, @PathVariable("slug") String slugProduct) {
		Optional<Product> optProduct = productService.findBySlug(slugProduct);
		if(optProduct.isPresent()) {
			Product product = optProduct.get();
			model.addAttribute("product", product);
			return new ModelAndView("admin/profile-product", model);
		}
		return new ModelAndView("forward:/admin/products");
	}
	@PostMapping("/ban/{slug}")
	public ModelAndView banProduct(ModelMap model, @PathVariable("slug") String slugProduct) {
		Optional<Product> optProduct = productService.findBySlug(slugProduct);
		if(optProduct.isPresent()) {
			Product product = optProduct.get();
			product.setBan(true);
			productService.save(product);
			model.addAttribute("product", product);
			return new ModelAndView("admin/profile-product", model);
		}
		return new ModelAndView("forward:/admin/products");
	}
	@PostMapping("/unban/{slug}")	
	public ModelAndView unbanProduct(ModelMap model, @PathVariable("slug") String slugProduct) {
		Optional<Product> optProduct = productService.findBySlug(slugProduct);
		if(optProduct.isPresent()) {
			Product product = optProduct.get();
			product.setBan(false);
			model.addAttribute("product", product);
			productService.save(product);
			return new ModelAndView("admin/profile-product", model);
		}
		return new ModelAndView("forward:/admin/products");
	}
}
