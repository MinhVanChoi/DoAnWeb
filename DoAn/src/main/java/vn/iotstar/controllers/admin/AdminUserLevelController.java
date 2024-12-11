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
import vn.iotstar.entity.UserLevel;
import vn.iotstar.services.UserLevelService;

@Controller
@RequestMapping("/admin/userlevel")
public class AdminUserLevelController {
	@Autowired
	UserLevelService userLevelService;
	@RequestMapping("")
	public String all(Model model) {
		List<UserLevel> list = userLevelService.findAll();
		model.addAttribute("listuserlevel", list);
		return "admin/userlevel-list";
	}
	@GetMapping("/add")
	public String add() {
		return "admin/userlevel-add";
	}
	@GetMapping("/edit/{id}")
	public ModelAndView edit(ModelMap model, @PathVariable("id") Long idUserLevel) {
		Optional<UserLevel> optUserLevel = userLevelService.findById(idUserLevel);
		if(optUserLevel.isPresent()) {
			UserLevel userLevel = optUserLevel.get();
			model.addAttribute("userlevel", userLevel);
			return new ModelAndView("admin/userlevel-edit", model);
		}
		return new ModelAndView("foward:/admin/userlevel");
	}
	@PostMapping("/insert")
	public ModelAndView insert(ModelMap model, @Valid @ModelAttribute("userlevel") UserLevel userLevelModel, BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/userlevel-add");
		}
		UserLevel userLevel = new UserLevel();
		BeanUtils.copyProperties(userLevelModel, userLevel);
		userLevelService.save(userLevel);
		return new ModelAndView("foward:/admin/userlevel");
	}
	@PostMapping("/update")
	public ModelAndView update(ModelMap model, @Valid @ModelAttribute("userlevel") UserLevel userLevelModel, BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/userlevel-edit");
		}
		UserLevel userLevel = new UserLevel();
		BeanUtils.copyProperties(userLevelModel, userLevel);
		userLevelService.save(userLevel);
		return new ModelAndView("foward:/admin/userlevel");
	}
	@GetMapping("delete/{id}")
	public ModelAndView delete(ModelMap model, @PathVariable("id") Long idUserLevel) {
		Optional<UserLevel> optUserLevel = userLevelService.findById(idUserLevel);
		if(optUserLevel.isPresent()) {
			UserLevel userLevel = optUserLevel.get();
			userLevel.setDelete(true);
			userLevelService.save(userLevel);
		}
		return new ModelAndView("redirect:/admin/userlevel");
	}
	@GetMapping("restore/{id}")
	public ModelAndView restore(ModelMap model, @PathVariable("id") Long idUserLevel) {
		Optional<UserLevel> optUserLevel = userLevelService.findById(idUserLevel);
		if(optUserLevel.isPresent()) {
			UserLevel userLevel = optUserLevel.get();
			userLevel.setDelete(false);
			userLevelService.save(userLevel);
		}
		return new ModelAndView("redirect:/admin/userlevel");
	}
}
