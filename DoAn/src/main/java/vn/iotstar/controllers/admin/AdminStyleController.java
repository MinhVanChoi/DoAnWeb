package vn.iotstar.controllers.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Style;
import vn.iotstar.services.StyleService;

@Controller
@RequestMapping("/admin/styles")
public class AdminStyleController {
	@Autowired
	StyleService styleService;
	
	
	
	private Page<Style> getPaginatedResult(Optional<Integer> page, Optional<Integer> size, String nameFilter) {
	    int currentPage = page.orElse(1);  
	    int pageSize = size.orElse(3);  
	    Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
	    
	    if (StringUtils.hasText(nameFilter)) {
	        return styleService.findByNameContaining(nameFilter, pageable);
	    } else {
	        return styleService.findAll(pageable);
	    }
	}
	
	
	
	@RequestMapping("")
	public String all(Model model, @RequestParam("page") Optional<Integer> page,
	        @RequestParam("size") Optional<Integer> size) {

	    Page<Style> resultPage = getPaginatedResult(page, size, null);
	    
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

	    model.addAttribute("stylePage", resultPage);  
	    model.addAttribute("pageNumbers", pageNumbers);    
	    return "admin/style-list"; 
	}
	
	@GetMapping("/add")
	public String add() {
		return "admin/style-add";
	}
	@GetMapping("/edit/{id}")
	public ModelAndView edit(ModelMap model, @PathVariable("id") Long idStyle) {
		Optional<Style> optStyle = styleService.findById(idStyle);
		if(optStyle.isPresent()) {
			Style style = optStyle.get();
			model.addAttribute("style", style);
			return new ModelAndView("admin/style-edit", model);
		}
		return new ModelAndView("forward:/admin/styles");
	}
	@PostMapping("/insert")
	public ModelAndView insert(ModelMap model, @Valid @ModelAttribute("style") Style styleModel,BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/style-add");
		}
		Style style = new Style();
		BeanUtils.copyProperties(styleModel, style);
		styleService.save(style);
		return new ModelAndView("forward:/admin/styles");
	}
	@PostMapping("/update")
	public ModelAndView update(ModelMap model, @Valid @ModelAttribute("style") Style styleModel,BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/style-edit");
		}
		Style style = new Style();
		BeanUtils.copyProperties(styleModel, style);
		styleService.save(style);
		return new ModelAndView("forward:/admin/styles");
	}
	@GetMapping("/delete/{id}")
	public ModelAndView delete(ModelMap model, @PathVariable("id") Long idStyle) {
		Optional<Style> optStyle = styleService.findById(idStyle);
		if(optStyle.isPresent()) {
			Style style = optStyle.get();
			style.setDelete(true);
			styleService.save(style);
			model.addAttribute("style", style);
		}
		return new ModelAndView("redirect:/admin/styles");
	}
	@GetMapping("/restore/{id}")
	public ModelAndView restore(ModelMap model, @PathVariable("id") Long idStyle) {
		Optional<Style> optStyle = styleService.findById(idStyle);
		if(optStyle.isPresent()) {
			Style style = optStyle.get();
			style.setDelete(false);
			styleService.save(style);
			model.addAttribute("style", style);
		}
		return new ModelAndView("redirect:/admin/styles");
	}
	
	@RequestMapping("/searchpaginated")
	public String search(ModelMap model,
	        @RequestParam(name = "name", required = false) String name,
	        @RequestParam("page") Optional<Integer> page,
	        @RequestParam("size") Optional<Integer> size) {

	    Page<Style> resultPage = getPaginatedResult(page, size, name);
	    
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

	    model.addAttribute("stylePage", resultPage); 
	    model.addAttribute("pageNumbers", pageNumbers);   
	    model.addAttribute("name", name); 
	    return "admin/style-list";  
	}
	
	
}
