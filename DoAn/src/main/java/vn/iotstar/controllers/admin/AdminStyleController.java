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
import vn.iotstar.entity.Style;
import vn.iotstar.services.StyleService;

@Controller
@RequestMapping("/admin/styles")
public class AdminStyleController {
	@Autowired
	StyleService styleService;
	@RequestMapping("")
	public String all(Model model) {
		List<Style> list = styleService.findAll();
		model.addAttribute("liststyle", list);
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
		return new ModelAndView("foward:/admin/styles");
	}
	@PostMapping("/insert")
	public ModelAndView insert(ModelMap model, @Valid @ModelAttribute("style") Style styleModel,BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/style-add");
		}
		Style style = new Style();
		BeanUtils.copyProperties(styleModel, style);
		styleService.save(style);
		return new ModelAndView("foward:/admin/styles");
	}
	@PostMapping("/update")
	public ModelAndView update(ModelMap model, @Valid @ModelAttribute("style") Style styleModel,BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/style-edit");
		}
		Style style = new Style();
		BeanUtils.copyProperties(styleModel, style);
		styleService.save(style);
		return new ModelAndView("foward:/admin/styles");
	}
	@GetMapping("/delete/{id}")
	public ModelAndView delete(ModelMap model, @PathVariable("id") Long idStyle) {
		Optional<Style> optStyle = styleService.findById(idStyle);
		if(optStyle.isPresent()) {
			Style style = optStyle.get();
			style.setDelete(true);
			model.addAttribute("style", style);
		}
		return new ModelAndView("redirect:/admin/style");
	}
	@GetMapping("/restore/{id}")
	public ModelAndView restore(ModelMap model, @PathVariable("id") Long idStyle) {
		Optional<Style> optStyle = styleService.findById(idStyle);
		if(optStyle.isPresent()) {
			Style style = optStyle.get();
			style.setDelete(false);
			model.addAttribute("style", style);
		}
		return new ModelAndView("redirect:/admin/style");
	}
}
