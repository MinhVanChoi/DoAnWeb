package vn.iotstar.controllers.store;

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

import jakarta.servlet.http.HttpSession;
import vn.iotstar.Constain;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Store;
import vn.iotstar.services.ProductService;

@Controller
@RequestMapping("/stores/products")
public class StoreProductController {
	@Autowired
	ProductService productService;

	@RequestMapping("")
	public String all(Model model, HttpSession session) {
		Store store = (Store) session.getAttribute("store");
		List<Product> products = productService.findByStore(store);
		model.addAttribute("listproduct", products);
		return "user/mystore-product";
	}
	@GetMapping("/selling-product")
	public String sellingProduct(Model model, HttpSession session) {
		Store store = (Store) session.getAttribute("store");
		List<Product> list = productService.findSellingProduct();
		List<Product> products = new ArrayList<>();
		for (Product product : list) {
			if(store.equals(product.getStore())) {
				products.add(product);
			}
		}
		model.addAttribute("listproduct", products);
		return "admin/product-list";
	}
	@GetMapping("stored-product")
	public String storedProduct(Model model, HttpSession session) {
		Store store = (Store) session.getAttribute("store");
		List<Product> list = productService.findStoredProduct();
		List<Product> products = new ArrayList<>();
		for (Product product : list) {
			if(store.equals(product.getStore())) {
				products.add(product);
			}
		}
		model.addAttribute("listproduct", products);
		return "admin/product-list";
	}
	@GetMapping("/add")
	public String add() {
		return "product-add";
	}
	
	@GetMapping("/edit/{slug}")
	public ModelAndView edit(ModelMap model, @PathVariable("slug") String slugProduct, HttpSession session) {
		Optional<Product> optProduct = productService.findBySlug(slugProduct);
		Store store = (Store) session.getAttribute("store");
		if (optProduct.isPresent()) {
			Product product = optProduct.get();
			if (store.equals(product.getStore())) {
				model.addAttribute("product", product);
				return new ModelAndView("product-edit", model);
			}
		}
		return new ModelAndView("forward:/stores/products");
	}

	@GetMapping("/selling/{slug}")
	public ModelAndView selling(ModelMap model, @PathVariable("slug") String slugProduct, HttpSession session) {
		Optional<Product> optProduct = productService.findBySlug(slugProduct);
		Store store = (Store) session.getAttribute("store");
		if (optProduct.isPresent()) {
			Product product = optProduct.get();
			if (store.equals(product.getStore())) {
				product.setSelling(true);
				productService.save(product);
			}
		}
		return new ModelAndView("redirect:/stores/products");
	}

	@GetMapping("/stored/{slug}")
	public ModelAndView stored(ModelMap model, @PathVariable("slug") String slugProduct, HttpSession session) {
		Optional<Product> optProduct = productService.findBySlug(slugProduct);
		Store store = (Store) session.getAttribute("store");
		if (optProduct.isPresent()) {
			Product product = optProduct.get();
			if (store.equals(product.getStore())) {
				product.setSelling(false);
				productService.save(product);
			}
		}
		return new ModelAndView("redirect:/stores/products");
	}

	@PostMapping("/insert")
	public ModelAndView insert(ModelMap model, @ModelAttribute("product") Product productModel, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			return new ModelAndView("product-add");
		}
		Store store = (Store) session.getAttribute("store");
		Product product = new Product();
		BeanUtils.copyProperties(productModel, product);
		product.setStore(store);
		product.setSlug(Constain.generateSlug(product.getName()));
		productService.save(product);
		return new ModelAndView("redirect:/stores/products");
	}

	@PostMapping("/update")
	public ModelAndView update(ModelMap model, @ModelAttribute("product") Product productModel, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			return new ModelAndView("product-edit");
		}
		Store store = (Store)session.getAttribute("store");
		Product product = new Product();
		BeanUtils.copyProperties(productModel, product);
		if(store.equals(product.getStore())) {
			product.setSlug(Constain.generateSlug(product.getName()));
		}
		productService.save(product);
		return new ModelAndView("redirect:/stores/products");
	}
}
