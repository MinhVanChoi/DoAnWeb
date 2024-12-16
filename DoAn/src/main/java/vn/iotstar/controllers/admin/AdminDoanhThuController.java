package vn.iotstar.controllers.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Order;
import vn.iotstar.entity.User;
import vn.iotstar.services.OrderService;
import vn.iotstar.services.UserService;


@Controller
@RequestMapping("/admin")
public class AdminDoanhThuController {
	
	
    @Autowired
    OrderService orderService;  // Dịch vụ xử lý đơn hàng

    @Autowired
    UserService userService;  // Dịch vụ xử lý người dùng
	
    @GetMapping("/doanhthu")
    public String index() {
        return "admin/doanhthu";
    }
    @GetMapping("/order")
    public String allOrders(Model model) {
        List<Order> orders = orderService.findAll();  

        model.addAttribute("listorder", orders);

        return "admin/order";  
    }
    
    
}

