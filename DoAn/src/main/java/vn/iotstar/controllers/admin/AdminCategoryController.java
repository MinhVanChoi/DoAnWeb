package vn.iotstar.controllers.admin;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.entity.Category;
import vn.iotstar.services.CategoryService;
import vn.iotstar.utils.Constain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {
	@Autowired
	CategoryService cateService;
	
	private Page<Category> getPaginatedResult(Optional<Integer> page, Optional<Integer> size, String nameFilter) {
	    int currentPage = page.orElse(1);  
	    int pageSize = size.orElse(3);    
	    Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
	    
	    if (StringUtils.hasText(nameFilter)) {
	        return cateService.findByNameContaining(nameFilter, pageable);
	    } else {
	        return cateService.findAll(pageable);
	    }
	}
	
	
	@RequestMapping("")
	public String all(Model model, @RequestParam("page") Optional<Integer> page,
	        @RequestParam("size") Optional<Integer> size) {

	    // Use the helper method to get the paginated result
	    Page<Category> resultPage = getPaginatedResult(page, size, null);
	    
	    // Handle pagination display
	    int totalPages = resultPage.getTotalPages();
	    List<Integer> pageNumbers = new ArrayList<>();
	    int currentPage = resultPage.getNumber() + 1;  // Get the current page (1-based)

	    // Generate page numbers (currentPage ± 2 range)
	    if (totalPages > 0) {
	        int start = Math.max(1, currentPage - 2);
	        int end = Math.min(currentPage + 2, totalPages);
	        for (int i = start; i <= end; i++) {
	            pageNumbers.add(i);
	        }
	    }

	    model.addAttribute("categoryPage", resultPage);  // Add paginated result to the model
	    model.addAttribute("pageNumbers", pageNumbers);    // Add page numbers for pagination
	    return "admin/category-list";  // Return the view
	}
	
	
	
	@GetMapping("/add")
	public String add(Model model) {
		Category category = new Category();
		model.addAttribute("category", category);
		return "admin/category-add";	
	}
	@GetMapping("/edit/{id}")
	public ModelAndView edit(ModelMap model, @PathVariable("id") Long cateid) {
		Optional<Category> optCate = cateService.findById(cateid);
		if(optCate.isPresent()) {
			Category cate = optCate.get();
			model.addAttribute("cate",cate);
			return new ModelAndView("admin/category-edit", model);
		}
		return new ModelAndView("forward:/admin/categories", model);
	}
	
	@GetMapping("/delete/{id}")
	public ModelAndView delete(ModelMap model, @PathVariable("id") Long cateid) {
		System.out.println("1");
		Optional<Category> optCate = cateService.findById(cateid);
		System.out.println(optCate);
		if(optCate.isPresent()) {
			Category cate = optCate.get();
			cate.setDelete(true);
			cateService.save(cate);
		}
		return new ModelAndView("redirect:/admin/categories");
	}
	
	
	
	@GetMapping("/restore/{id}")
	public ModelAndView restore(ModelMap model, @PathVariable("id") Long cateid) {
		Optional<Category> optCate = cateService.findById(cateid);
		if(optCate.isPresent()) {
			Category cate = optCate.get();
			cate.setDelete(false);
			cateService.save(cate);
		}
		return new ModelAndView("redirect:/admin/categories");
	}
	@PostMapping("/insert")
	public ModelAndView insert(ModelMap model, @Valid @ModelAttribute("category") Category categoryModel,BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/category-add");
		}
		Category category = new Category();
		BeanUtils.copyProperties(categoryModel, category);
		category.setSlug(Constain.generateSlug(category.getName()));
		cateService.save(category);
		return new ModelAndView("forward:/admin/categories", model);
	}
	
	@PostMapping("/update")
	public ModelAndView update(ModelMap model, @Valid @ModelAttribute("category") Category categoryModel,BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/category-edit");
		}
		Category category = new Category();
		BeanUtils.copyProperties(categoryModel, category);
		Optional<Category> optCate = cateService.findById(category.getId());
		Category categoryold = optCate.get();
		category.setSlug(Constain.generateSlug(category.getName()));
		category.setCreateAt(categoryold.getCreateAt());
		cateService.save(category);
		return new ModelAndView("forward:/admin/categories", model);
	}
	

	@RequestMapping("/searchpaginated")
	public String search(ModelMap model,
	        @RequestParam(name = "name", required = false) String name,
	        @RequestParam("page") Optional<Integer> page,
	        @RequestParam("size") Optional<Integer> size) {

	    Page<Category> resultPage = getPaginatedResult(page, size, name);
	    
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

	    model.addAttribute("categoryPage", resultPage); 
	    model.addAttribute("pageNumbers", pageNumbers);    
	    model.addAttribute("name", name);  
	    return "admin/category-list"; 
	}

}
