package vn.iotstar.controllers.admin;

import java.security.PublicKey;
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

import vn.iotstar.Constain;
import vn.iotstar.entity.Category;
import vn.iotstar.services.CategoryService;
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
	@RequestMapping("")
	public String all(Model model) {
		List<Category> categories = cateService.findAll();
		model.addAttribute("listcate", categories);
		return "admin/category-list";
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
	    int count = (int) cateService.count();
	    int currentPage = page.orElse(1);  // Default to page 1 if not provided
	    int pageSize = size.orElse(3);     // Default to size 3 if not provided
	    Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
	    Page<Category> resultPage;

	    // Check if the 'name' parameter is provided and filter based on it
	    if (StringUtils.hasText(name)) {
	        resultPage = cateService.findByNameContaining(name, pageable);
	        model.addAttribute("name", name);  // Store 'name' in the model for search input box
	    } else {
	        resultPage = cateService.findAll(pageable);
	    }

	    // Handle pagination
	    int totalPages = resultPage.getTotalPages();
	    if (totalPages > 0) {
	        int start = Math.max(1, currentPage - 2);
	        int end = Math.min(currentPage + 2, totalPages);
	        if (totalPages > count) {
	            if (end == totalPages) {
	                start = end - count;
	            } else if (start == 1) {
	                end = start + count;
	            }
	        }
	        List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
	                .boxed()
	                .collect(Collectors.toList());
	        model.addAttribute("pageNumbers", pageNumbers);
	    }

	    model.addAttribute("categoryPage", resultPage);  // Add resultPage to the model
	    return "admin/category-list";  // Return the view
	}

}
