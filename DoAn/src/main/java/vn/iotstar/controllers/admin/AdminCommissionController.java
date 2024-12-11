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
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Commission;
import vn.iotstar.services.CommissionService;

@Controller
@RequestMapping("/admin/commissions")
public class AdminCommissionController {
	@Autowired
	CommissionService commissionService;
	@RequestMapping("")
	public String all(Model model) {
		List<Commission> list = commissionService.findAll();
		model.addAttribute("listcommission", list);
		return "admin/commission-list";
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
}
