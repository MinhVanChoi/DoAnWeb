package vn.iotstar.controllers.store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Order;
import vn.iotstar.entity.Store;
import vn.iotstar.services.OrderService;

@Controller
@RequestMapping("stores/orders")
public class StoreOrderController {
	@Autowired
	OrderService orderService;
	@RequestMapping("")
	public String all(Model model, HttpSession session) {
		Store store = (Store) session.getAttribute("store");
		List<Order> orders = orderService.findByStore(store);
		model.addAttribute("listorder", orders);
		return "store/order-list";
	}
}
