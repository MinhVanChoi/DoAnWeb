package vn.iotstar.controllers.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.entity.Order;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;
import vn.iotstar.services.OrderService;
import vn.iotstar.services.ProductService;
import vn.iotstar.services.StoreService;
import vn.iotstar.services.UserService;


@Controller
@RequestMapping("/admin")
public class AdminHomeController {
	
    @Autowired
	StoreService storeService;
	
    @Autowired
	UserService userService;
    
    @Autowired
   	ProductService productService;
    
    @Autowired
   	OrderService orderService;
    
    @GetMapping("/home")
    public String index(Model model) {
    	
        List<Store> stores = storeService.findAll();  
        int storeCount = stores.size();
        
        List<User> users = userService.findAll();  
        int userCount = users.size();
        
        List<Product> products = productService.findAll();  
        int productCount = products.size();
        
        List<Order> orders = orderService.findAll();  
        double totalAmountSum = 0.0;

        for (Order order : orders) {
            totalAmountSum += order.getTotalAmount();  
        }

        

        model.addAttribute("userCount", userCount);
        model.addAttribute("storeCount", storeCount);
        model.addAttribute("productCount", productCount);
        model.addAttribute("totalAmountSum", totalAmountSum);

        
        
        return "admin/home";
    }
    
}
