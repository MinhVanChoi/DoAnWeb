package vn.iotstar.controllers.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		return "/admin/product-list";
	}
	@GetMapping("/banned-product")
	public String bannedProduct(Model model) {
		List<Product> list = productService.findBannedProduct();
		model.addAttribute("listproduct", list);
		return "/admin/product-list";
	}
	@GetMapping("/unbanned-product")
	public String unBannedProduct(Model model) {
		List<Product> list = productService.findUnBannedProduct();
		model.addAttribute("listproduct", list);
		return "/admin/product-list";
	}
	
}
