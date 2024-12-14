package vn.iotstar.controllers.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.iotstar.entity.Address;
import vn.iotstar.entity.Category;
import vn.iotstar.services.AddressService;

@Controller
public class MapController {
	
    @Autowired
	AddressService addressService;
@GetMapping("/showMap")
public String index() {
	return "showmap";
}
	
@GetMapping("/ToaDo")
public String ToaDo() {
	return "customer/layToaDo";
}

@GetMapping("/duongdi")
public String Duongden2diadiem() {
	return "duongdi";
}


@GetMapping("/testdiachi")
public String testdiachi(Model model) {
	 List<Address> listlocation = addressService.findAll();
     model.addAttribute("listlocation", listlocation);
	return "testdiachi";
	
	
}


@GetMapping("/themdiadiem")
@ResponseBody 
public List<Address> getAllLocations() {
	  return addressService.findAll(); //
}







}
