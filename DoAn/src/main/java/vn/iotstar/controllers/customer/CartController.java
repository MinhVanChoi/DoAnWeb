package vn.iotstar.controllers.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.iotstar.entity.Address;
import vn.iotstar.entity.Cart;
import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;
import vn.iotstar.services.StoreService;

@Controller
@RequestMapping("/customer")

public class CartController {
	
	
	List<Address> address = new ArrayList<>();
	@Autowired 
	StoreService storeService;
	  @GetMapping("checkout")
		public String viewCart(Model model, HttpSession session) {
			User user = (User)session.getAttribute("user");
		    if (user == null) {
		        // Redirect to the home page if the user is not logged in
		        return "redirect:/home";
		    }
			Cart cart = user.getCart();
			 if (cart == null) {
			        return "redirect:/product";
			    }
			List<CartItem> cartitems = cart.getCartItems();
			List<Product> product_test = new ArrayList<>();
			for(CartItem item : cartitems) {
				Product product_1 = item.getProduct();
				product_test.add(product_1);
			}
			
			double totalAmount = 0;
			for (CartItem item : cartitems) {
			    totalAmount += item.getProduct().getPrice() * item.getCount();
			}
			

			  // Lấy thông tin vĩ độ và kinh độ của User
	        double userLat = user.getLatitude();
	        double userLon = user.getLongitude();

	        // Lấy danh sách tất cả các store từ cơ sở dữ liệu
	        List<Store> stores = storeService.findAll();
	        
	        // Tính khoảng cách và tìm store gần nhất
	        Store nearestStore = null;
	        double minDistance = Double.MAX_VALUE; // Khoảng cách nhỏ nhất ban đầu là vô cùng lớn
	        
	        for (Store store : stores) {
	            // Tính khoảng cách giữa User và Store
	            double distance = calculateDistance(userLat, userLon, store.getLatitude(), store.getLongitude());

	            // Nếu khoảng cách hiện tại nhỏ hơn khoảng cách nhỏ nhất, cập nhật nearestStore
	            if (distance < minDistance) {
	                minDistance = distance;
	                nearestStore = store;
	            }
	        }
	        
	        // Thêm địa chỉ người dùng vào danh sách
	        Address userAddress = new Address();
	        userAddress.setName(user.getFullname());
	        userAddress.setLatitude(user.getLatitude());
	        userAddress.setLongitude(user.getLongitude());        
	        
	        Address storeAddress = new Address();
            storeAddress.setName(nearestStore.getName());
            storeAddress.setLatitude(nearestStore.getLatitude());
            storeAddress.setLongitude(nearestStore.getLongitude());
			
            address.add(userAddress);
            address.add(storeAddress);
	        double shippingCost = calculateShippingCost(minDistance);
			
	        double MaxTotalAmount = totalAmount + shippingCost;
	        
	        model.addAttribute("listcaritems",cartitems);
			model.addAttribute("totalAmount", totalAmount); 
		    model.addAttribute("nearestStore", nearestStore);
		    model.addAttribute("minDistance", minDistance);
	        model.addAttribute("shippingCost", shippingCost);
	        model.addAttribute("MaxTotalAmount", MaxTotalAmount);
			model.addAttribute("cart",cart);

			return "checkout";
		}
	  
	  
	  
	  @GetMapping("/khoangcach2vitri")
	  @ResponseBody 
	  public List<Address> KhoangCach2ViTriTrenMap() {
		  System.out.println(address);
			return address;
		  
	  }

	  

	  // Hàm tính khoảng cách giữa 2 tọa độ
	    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
	        final double R = 6371; // Bán kính Trái Đất tính bằng km
	        double dLat = Math.toRadians(lat2 - lat1);
	        double dLon = Math.toRadians(lon2 - lon1);

	        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
	                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
	        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	        double distance = R * c;
	        return Math.round(distance * 100.0) / 100.0;
	    }
	  
	    public double calculateShippingCost(double distance) {
	        double cost = 0;

	        if (distance <= 1) {
	            cost = 16000; 
	        } else if (distance > 1 && distance <= 3) {
	            cost = 16000 + (distance - 1) * 5000;
	        } else {
	            cost = 16000 + 2 * 5000 + (distance - 3) * 10000; 
	        }

	        return Math.round(cost);
	    }
	  
	    
	    
	  
	  
	  
	
}
