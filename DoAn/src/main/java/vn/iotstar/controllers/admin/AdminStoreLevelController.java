package vn.iotstar.controllers.admin;

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

import jakarta.validation.Valid;
import vn.iotstar.entity.StoreLevel;
import vn.iotstar.services.StoreLevelService;

@Controller
@RequestMapping("/admin/storelevel")
public class AdminStoreLevelController {
	@Autowired
	StoreLevelService storeLevelService;
	@RequestMapping("")
	public String all(Model model) {
		List<StoreLevel> list = storeLevelService.findAll();
		model.addAttribute("listuserlevel", list);
		return "admin/storelevel-list";
	}
	@GetMapping("/add")
	public String add() {
		return "admin/storelevel-add";
	}
	@GetMapping("edit/{id}")
	public ModelAndView edit(ModelMap model, @PathVariable("id") Long idStoreLevel) {
		Optional<StoreLevel> optStoreLevel = storeLevelService.findById(idStoreLevel);
		if(optStoreLevel.isPresent()) {
			StoreLevel storeLevel = optStoreLevel.get();
			model.addAttribute("storelevel", storeLevel);
			return new ModelAndView("admin/storelevel-edit");
		}
		return new ModelAndView("foward:/admin/storelevel");
	}
	@PostMapping("/insert")
	public ModelAndView insert(ModelMap model, @Valid @ModelAttribute("storelevel") StoreLevel storeLevelModel, BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/storelevel-add");
		}
		StoreLevel storeLevel = new StoreLevel();
		BeanUtils.copyProperties(storeLevelModel, storeLevel);
		storeLevelService.save(storeLevel);
		return new ModelAndView("foward:/admin/storelevel");
	}
	@PostMapping("/update")
	public ModelAndView update(ModelMap model, @Valid @ModelAttribute("storelevel") StoreLevel storeLevelModel, BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/storelevel-edit");
		}
		StoreLevel storeLevel = new StoreLevel();
		BeanUtils.copyProperties(storeLevelModel, storeLevel);
		storeLevelService.save(storeLevel);
		return new ModelAndView("foward:/admin/storelevel");
	}
	@GetMapping("/delete/{id}")
	public ModelAndView delete(ModelMap model, @PathVariable("id") Long idStoreLevel) {
		Optional<StoreLevel> optStoreLevel = storeLevelService.findById(idStoreLevel);
		if(optStoreLevel.isPresent()) {
			StoreLevel storeLevel = optStoreLevel.get();
			storeLevel.setDelete(true);
			storeLevelService.save(storeLevel);
		}
		return new ModelAndView("redirect:/admin/storelevel");
	}
	@GetMapping("/restore/{id}")
	public ModelAndView restore(ModelMap model, @PathVariable("id") Long idStoreLevel) {
		Optional<StoreLevel> optStoreLevel = storeLevelService.findById(idStoreLevel);
		if(optStoreLevel.isPresent()) {
			StoreLevel storeLevel = optStoreLevel.get();
			storeLevel.setDelete(false);
			storeLevelService.save(storeLevel);
		}
		return new ModelAndView("redirect:/admin/storelevel");
	}
}
