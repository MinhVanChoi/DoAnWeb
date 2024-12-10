package vn.iotstar.controllers.admin;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String add() {
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
	@PostMapping("/delete/{id}")
	public ModelAndView delete(ModelMap model, @PathVariable("id") Long cateid) {
		Optional<Category> optCate = cateService.findById(cateid);
		if(optCate.isPresent()) {
			Category cate = optCate.get();
			cate.setDelete(true);
			cateService.save(cate);
		}
		return new ModelAndView("redirect:/admin/categories");
	}
	@PostMapping("/restore/{id}")
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
		cateService.save(category);
		return new ModelAndView("forward:/admin/categories", model);
	}
}
