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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;
import vn.iotstar.services.UserService;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
	

	private Page<User> getPaginatedResult(Optional<Integer> page, Optional<Integer> size, String nameFilter) {
	    int currentPage = page.orElse(1);  
	    int pageSize = size.orElse(3);  
	    Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("fullname"));
	    
	    if (StringUtils.hasText(nameFilter)) {
	        return userService.findByFullnameContaining(nameFilter, pageable);
	    } else {
	        return userService.findAll(pageable);
	    }
	}
	
	
	@Autowired
	UserService userService;
	
	@RequestMapping("")
	public String all(Model model, @RequestParam("page") Optional<Integer> page,
	        @RequestParam("size") Optional<Integer> size) {

	    Page<User> resultPage = getPaginatedResult(page, size, null);
	    
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
	    
	    model.addAttribute("userPage", resultPage);  
	    model.addAttribute("pageNumbers", pageNumbers);    
	    return "admin/user-list"; 
	}
	
	
	@GetMapping("/{slug}")
	public ModelAndView viewUser(ModelMap model, @PathVariable("slug") String slugUser) {
		Optional<User> optUser = userService.findBySlug(slugUser);
		if(optUser.isPresent()) {
			User user = optUser.get();
			model.addAttribute("user", user);
			return new ModelAndView("/admin/profile-user", model);
		}
		return new ModelAndView("foward:/admin/users", model);
	}
	@PostMapping("/ban/{slug}")
	public ModelAndView banUser(ModelMap model, @PathVariable("slug") String slugUser) {
		Optional<User> optUser = userService.findBySlug(slugUser);
		if(optUser.isPresent()) {
			User user = optUser.get();
			user.setBan(true);
			userService.save(user);
			model.addAttribute("user", user);
			return new ModelAndView("/admin/profile-user", model);
		}
		return new ModelAndView("foward:/admin/users", model);
	}
	

	@RequestMapping("/searchpaginated")
	public String search(ModelMap model,
	        @RequestParam(name = "name", required = false) String name,
	        @RequestParam("page") Optional<Integer> page,
	        @RequestParam("size") Optional<Integer> size) {

	    // Use the helper method to get the paginated result
	    Page<User> resultPage = getPaginatedResult(page, size, name);
	    
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

	    model.addAttribute("userPage", resultPage);  // Add paginated result to the model
	    model.addAttribute("pageNumbers", pageNumbers);    // Add page numbers for pagination
	    model.addAttribute("fullname", name);  // Add 'name' parameter for filtering
	    return "admin/user-list"; 
	}
	
	
	
	
	
}
