package vn.iotstar.controllers.admin;

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
import org.springframework.web.bind.annotation.RequestMapping;

import vn.iotstar.entity.Product;
import vn.iotstar.entity.Store;
import vn.iotstar.services.StoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("admin/stores")
public class AdminStoreController {
	@Autowired
	StoreService storeService;
	
	private Page<Store> getPaginatedResult(Optional<Integer> page, Optional<Integer> size, String nameFilter) {
	    int currentPage = page.orElse(1);  
	    int pageSize = size.orElse(3);  
	    Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
	    
	    if (StringUtils.hasText(nameFilter)) {
	        return storeService.findByNameContaining(nameFilter, pageable);
	    } else {
	        return storeService.findAll(pageable);
	    }
	}
	
	
	
	

	@RequestMapping("")
	public String all(Model model, @RequestParam("page") Optional<Integer> page,
	        @RequestParam("size") Optional<Integer> size) {

	    Page<Store> resultPage = getPaginatedResult(page, size, null);
	    
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
	    
	    model.addAttribute("storePage", resultPage);  
	    model.addAttribute("pageNumbers", pageNumbers);    
	    return "admin/store-list"; 
	}
	
	
	@GetMapping("/{slug}")
	public ModelAndView viewStore(ModelMap model, @PathVariable("slug") String slugStore) {
		Optional<Store> optStore = storeService.findBySlug(slugStore);
		if(optStore.isPresent()) {
			Store store = optStore.get();
			model.addAttribute("store", store);
			return new ModelAndView("admin/profile-store", model);
		}
		return new ModelAndView("forward:/admin/stores");
	}
	@PostMapping("/ban/{slug}")
	public ModelAndView banStore(ModelMap model, @PathVariable("slug") String slugStore) {
		Optional<Store> optStore = storeService.findBySlug(slugStore);
		if(optStore.isPresent()) {
			Store store = optStore.get();
			store.setBan(true);
			model.addAttribute("store", store);
			storeService.save(store);
		}
		
		return new ModelAndView("redirect:/admin/stores");
	}
	
	
	@RequestMapping("/searchpaginated")
	public String search(ModelMap model,
	        @RequestParam(name = "name", required = false) String name,
	        @RequestParam("page") Optional<Integer> page,
	        @RequestParam("size") Optional<Integer> size) {

	    // Use the helper method to get the paginated result
	    Page<Store> resultPage = getPaginatedResult(page, size, name);
	    
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

	    model.addAttribute("storePage", resultPage);  // Add paginated result to the model
	    model.addAttribute("pageNumbers", pageNumbers);    // Add page numbers for pagination
	    model.addAttribute("name", name);  // Add 'name' parameter for filtering
	    return "admin/store-list";  // Return the view
	}
}
