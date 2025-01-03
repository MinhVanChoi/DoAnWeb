package vn.iotstar.controllers.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.iotstar.entity.Product;
import vn.iotstar.services.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;
	

	private Page<Product> getPaginatedResult(Optional<Integer> page, Optional<Integer> size, String nameFilter) {
	    int currentPage = page.orElse(1);  
	    int pageSize = size.orElse(3);  
	    Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
	    
	    if (StringUtils.hasText(nameFilter)) {
	        return productService.findByNameContaining(nameFilter, pageable);
	    } else {
	        return productService.findAll(pageable);
	    }
	}
	

	@RequestMapping("")
	public String listProducts(Model model,
							   @RequestParam(defaultValue = "0") int page,
							   @RequestParam(defaultValue = "3") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> productPage = productService.findAll(pageable);
		model.addAttribute("productPage", productPage);
		return "product";
	}


	@GetMapping("/travai")
	public String travai(Model model, @RequestParam("page") Optional<Integer> page,
	        @RequestParam("size") Optional<Integer> size) {

	    Page<Product> resultPage = getPaginatedResult(page, size, "tra vai");
	    
	    int totalPages = resultPage.getTotalPages();
	    List<Integer> pageNumbers = new ArrayList<>();
	    int currentPage = resultPage.getNumber() + 1;  

	    if (totalPages > 0) {
	        int start = Math.max(1, currentPage - 2);
	        int end = Math.min(currentPage + 2, totalPages);
	        for (int i = start; i <= end; i++) {
	            pageNumbers.add(i);
	        }
	    }

	    model.addAttribute("productPage", resultPage);  
	    model.addAttribute("pageNumbers", pageNumbers);    
	    return "product-list"; 
	}
	

	@GetMapping("/trasua")
	public String trasua(Model model, @RequestParam("page") Optional<Integer> page,
	        @RequestParam("size") Optional<Integer> size) {

	    Page<Product> resultPage = getPaginatedResult(page, size, "tra sua");
	    
	    int totalPages = resultPage.getTotalPages();
	    List<Integer> pageNumbers = new ArrayList<>();
	    int currentPage = resultPage.getNumber() + 1;  

	    if (totalPages > 0) {
	        int start = Math.max(1, currentPage - 2);
	        int end = Math.min(currentPage + 2, totalPages);
	        for (int i = start; i <= end; i++) {
	            pageNumbers.add(i);
	        }
	    }

	    model.addAttribute("productPage", resultPage);  
	    model.addAttribute("pageNumbers", pageNumbers);    
	    return "product"; 
	}
	
	@GetMapping("/hongtra")
	public String hongtra(Model model, @RequestParam("page") Optional<Integer> page,
	        @RequestParam("size") Optional<Integer> size) {

	    Page<Product> resultPage = getPaginatedResult(page, size, "hong tra");
	    
	    int totalPages = resultPage.getTotalPages();
	    List<Integer> pageNumbers = new ArrayList<>();
	    int currentPage = resultPage.getNumber() + 1;  

	    if (totalPages > 0) {
	        int start = Math.max(1, currentPage - 2);
	        int end = Math.min(currentPage + 2, totalPages);
	        for (int i = start; i <= end; i++) {
	            pageNumbers.add(i);
	        }
	    }

	    model.addAttribute("productPage", resultPage);  
	    model.addAttribute("pageNumbers", pageNumbers);    
	    return "product-list";
	}
	
	@GetMapping("/traxanh")
	public String traxanh(Model model, @RequestParam("page") Optional<Integer> page,
	        @RequestParam("size") Optional<Integer> size) {

	    Page<Product> resultPage = getPaginatedResult(page, size, "tra vai");
	    
	    int totalPages = resultPage.getTotalPages();
	    List<Integer> pageNumbers = new ArrayList<>();
	    int currentPage = resultPage.getNumber() + 1;  

	    if (totalPages > 0) {
	        int start = Math.max(1, currentPage - 2);
	        int end = Math.min(currentPage + 2, totalPages);
	        for (int i = start; i <= end; i++) {
	            pageNumbers.add(i);
	        }
	    }

	    model.addAttribute("productPage", resultPage);  
	    model.addAttribute("pageNumbers", pageNumbers);    
	    return "product";
	}
	
	
	@GetMapping("/{slug}")
	public ModelAndView viewProduct(ModelMap model, @PathVariable("slug") String slugProduct) {
		Optional<Product> optProduct = productService.findBySlug(slugProduct);
		if(optProduct.isPresent()) {
			Product product = optProduct.get();
			model.addAttribute("product", product);
			return new ModelAndView("profile-product", model);
		}
		return new ModelAndView("forward:/admin/products");
	}
	
	
	

	@RequestMapping("/searchpaginated")
	public String search(ModelMap model,
	        @RequestParam(name = "name", required = false) String name,
	        @RequestParam("page") Optional<Integer> page,
	        @RequestParam("size") Optional<Integer> size) {

	    Page<Product> resultPage = getPaginatedResult(page, size, name);
	    
	    int totalPages = resultPage.getTotalPages();
	    List<Integer> pageNumbers = new ArrayList<>();
	    int currentPage = resultPage.getNumber() + 1;  
	    if (totalPages > 0) {
	        int start = Math.max(1, currentPage - 2);
	        int end = Math.min(currentPage + 2, totalPages);
	        for (int i = start; i <= end; i++) {
	            pageNumbers.add(i);
	        }
	    }

	    model.addAttribute("productPage", resultPage); 
	    model.addAttribute("pageNumbers", pageNumbers);   
	    model.addAttribute("name", name);  
	    return "product";  
	}
	
	
}

