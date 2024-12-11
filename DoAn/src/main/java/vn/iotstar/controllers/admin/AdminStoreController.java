package vn.iotstar.controllers.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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
	@RequestMapping("")
	public String all(Model model) {
		List<Store> list = storeService.findAll();
		model.addAttribute("liststore", list);
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
}
