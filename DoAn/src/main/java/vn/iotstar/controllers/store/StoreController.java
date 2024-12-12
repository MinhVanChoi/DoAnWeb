package vn.iotstar.controllers.store;

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
import jakarta.validation.Valid;
import vn.iotstar.Constain;
import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;
import vn.iotstar.services.StoreService;

@Controller
@RequestMapping("/stores")
public class StoreController {
	@Autowired
	StoreService storeService;
	@RequestMapping("")
	public String all(Model model, HttpSession session) {
		User user = (User)session.getAttribute("user");
		List<Store> list = storeService.findByOwner(user);
		model.addAttribute("liststore", list);
		return "user/store-list";
	}
	
	
	@GetMapping("/{slug}")
	public String myStore(Model model,@PathVariable("slug") String slugStore, HttpSession session) {
		User user = (User)session.getAttribute("user");
		Optional<Store> optStore = storeService.findBySlug(slugStore);
		if(optStore.isPresent()) {
			Store store = optStore.get();
			if(user.equals(store.getOwner())){
				model.addAttribute("store", store);
				session.setAttribute("store", store);
				return "user/mystore";
			}
		}
		return "index"; 
	}
	@GetMapping("/add")
	public String add() {
		return "user/store-add";
	}

	@GetMapping("/edit/{slug}")
	public ModelAndView edit(ModelMap model, @PathVariable("slug") String slugStore, HttpSession session) {
		User user = (User) session.getAttribute("user");
		Optional<Store> optStore = storeService.findBySlug(slugStore);
		if (optStore.isPresent()) {
			Store store = optStore.get();
			if (user.equals(store.getOwner())) {
				model.addAttribute("store", store);
				return new ModelAndView("user/store-edit", model);
			}
		}
		return new ModelAndView("foward:/stores");
	}
	@GetMapping("/close/{slug}")
	public ModelAndView close(ModelMap model, @PathVariable("slug") String slugStore, HttpSession session) {
		User user = (User) session.getAttribute("user");
		Optional<Store> optStore = storeService.findBySlug(slugStore);
		if (optStore.isPresent()) {
			Store store = optStore.get();
			if (user.equals(store.getOwner())) {
				store.setOpen(false);
				storeService.save(store);
			}
		}
		return new ModelAndView("foward:/stores");
	}
	@GetMapping("/open/{slug}")
	public ModelAndView open(ModelMap model, @PathVariable("slug") String slugStore, HttpSession session) {
		User user = (User) session.getAttribute("user");
		Optional<Store> optStore = storeService.findBySlug(slugStore);
		if (optStore.isPresent()) {
			Store store = optStore.get();
			if (user.equals(store.getOwner())) {
				store.setOpen(true);
				storeService.save(store);
			}
		}
		return new ModelAndView("foward:/stores");
	}
	@PostMapping("/insert")
	public ModelAndView insert(ModelAndView model, @Valid @ModelAttribute("store") Store storeModel, HttpSession session, BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("user/store-add");
		}
		User user = (User) session.getAttribute("user");
		Store store = new Store();
		BeanUtils.copyProperties(storeModel, store);
		store.setSlug(Constain.generateSlug(store.getName()));
		store.setOwner(user);
		storeService.save(store);
		return new ModelAndView("foward:/stores");
	}
	@PostMapping("/update")
	public ModelAndView update(ModelAndView model, @Valid @ModelAttribute("store") Store storeModel, HttpSession session, BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("user/store-add");
		}
		User user = (User) session.getAttribute("user");
		Store store = new Store();
		BeanUtils.copyProperties(storeModel, store);
		if(user.equals(store.getOwner())) {
			storeService.save(store);
		}
		return new ModelAndView("foward:/stores");
	}
}
