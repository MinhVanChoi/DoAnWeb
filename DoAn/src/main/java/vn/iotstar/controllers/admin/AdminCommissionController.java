package vn.iotstar.controllers.admin;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Commission;
import vn.iotstar.services.CommissionService;

@Controller
@RequestMapping("/admin/commissions")
public class AdminCommissionController {
	@Autowired
	CommissionService commissionService;
	
	
	private Page<Commission> getPaginatedResult(Optional<Integer> page, Optional<Integer> size, String nameFilter) {
	    int currentPage = page.orElse(1);  
	    int pageSize = size.orElse(3);  
	    Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
	    
	    if (StringUtils.hasText(nameFilter)) {
	        return commissionService.findByNameContaining(nameFilter, pageable);
	    } else {
	        return commissionService.findAll(pageable);
	    }
	}
	
	@RequestMapping("")
	public String all(Model model, @RequestParam("page") Optional<Integer> page,
	        @RequestParam("size") Optional<Integer> size) {

	    // Use the helper method to get the paginated result
	    Page<Commission> resultPage = getPaginatedResult(page, size, null);
	    
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

	    model.addAttribute("commissionPage", resultPage);  // Add paginated result to the model
	    model.addAttribute("pageNumbers", pageNumbers);    // Add page numbers for pagination
	    return "admin/commission-list";  // Return the view
	}
	
	
	@GetMapping("/add")
	public String add() {
		return "/admin/commission-add";
	}
	@GetMapping("/edit/{id}")
	public ModelAndView edit(ModelMap model, @PathVariable("id") Long idCommission) {
		Optional<Commission> optCommission = commissionService.findById(idCommission);
		if(optCommission.isPresent()) {
			Commission commission = optCommission.get();
			model.addAttribute("commission", commission);
			return new ModelAndView("admin/commission-edit");
		}
		return new ModelAndView("forward:/admin/commissions");
	}
	@PostMapping("/insert")
	public ModelAndView insert(ModelMap model, @Valid @ModelAttribute("commission") Commission commissionModel, BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/commission-add");
		}
		
		
		Commission commission = new Commission();
		BeanUtils.copyProperties(commissionModel, commission);
		commissionService.save(commission);
		return new ModelAndView("forward:/admin/commissions",model);
	}
	
	
	@PostMapping("/update")
	public ModelAndView update(ModelMap model, @Valid @ModelAttribute("commission") Commission commissionModel, BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/commission-add");
		}
		Commission commission = new Commission();
		BeanUtils.copyProperties(commissionModel, commission);
		Optional<Commission> optComm = commissionService.findById(commission.getId());
		Commission commissionold = optComm.get();
		commission.setCreateAt(commissionold.getCreateAt());
		commissionService.save(commission);
		return new ModelAndView("forward:/admin/commissions");
	}
	@GetMapping("/delete/{id}")
	public ModelAndView delete(ModelMap model, @PathVariable("id") Long idCommission) {
		Optional<Commission> optCommission = commissionService.findById(idCommission);
		if(optCommission.isPresent()) {
			Commission commission = optCommission.get();
			commission.setDelete(true);
			commissionService.save(commission);
		}
		return new ModelAndView("redirect:/admin/commissions");
	}
	@GetMapping("/restore/{id}")
	public ModelAndView restore(ModelMap model, @PathVariable("id") Long idCommission) {
		Optional<Commission> optCommission = commissionService.findById(idCommission);
		if(optCommission.isPresent()) {
			Commission commission = optCommission.get();
			commission.setDelete(true);
			commissionService.save(commission);
		}
		return new ModelAndView("redirect:/admin/commissions");
	}
	
	@RequestMapping("/searchpaginated")
	public String search(ModelMap model,
	        @RequestParam(name = "name", required = false) String name,
	        @RequestParam("page") Optional<Integer> page,
	        @RequestParam("size") Optional<Integer> size) {

	    // Use the helper method to get the paginated result
	    Page<Commission> resultPage = getPaginatedResult(page, size, name);
	    
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

	    model.addAttribute("commissionPage", resultPage);  // Add paginated result to the model
	    model.addAttribute("pageNumbers", pageNumbers);    // Add page numbers for pagination
	    model.addAttribute("name", name);  // Add 'name' parameter for filtering
	    return "admin/commission-list";  // Return the view
	}
}
