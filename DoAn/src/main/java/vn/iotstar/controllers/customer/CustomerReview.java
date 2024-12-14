//package vn.iotstar.controllers.customer;
//
//import java.util.Optional;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import jakarta.servlet.http.HttpSession;
//import jakarta.validation.Valid;
//import vn.iotstar.entity.Order;
//import vn.iotstar.entity.Product;
//import vn.iotstar.entity.ReviewOrder;
//import vn.iotstar.entity.ReviewProduct;
//import vn.iotstar.entity.Store;
//import vn.iotstar.entity.User;
//import vn.iotstar.services.OrderService;
//import vn.iotstar.services.ProductService;
//import vn.iotstar.services.ReviewOrderService;
//import vn.iotstar.services.ReviewProductService;
//
//@Controller
//@RequestMapping("/review")
//public class CustomerReview {
//	@Autowired
//	OrderService orderService;
//	@Autowired
//	ReviewOrderService reviewOrderService;
//	@Autowired
//	ProductService productService;
//	@Autowired
//	ReviewProductService reviewProductService;
//	
//	
//	@GetMapping("/order/{id}")
//	public String reviewOrder(Model model, @PathVariable("id") Long idOrder,HttpSession session) {
//		User user = (User)session.getAttribute("user");
//		Optional<Order> optOrder = orderService.findById(idOrder);
//		if(optOrder.isPresent()) {
//			Order order = optOrder.get();
//			Store store = order.getStore();
//			ReviewOrder reviewOrder = new ReviewOrder();
//			reviewOrder.setUser(user);
//			reviewOrder.setStore(store);
//			reviewOrder.setOrder(order);
//			model.addAttribute("reviewOrder", reviewOrder);
//		}
//		return "review-order";
//	}
//	@PostMapping("/reviewed-order")
//	public ModelAndView reviewedOrder(ModelMap model,@Valid @ModelAttribute("reviewOrder") ReviewOrder reviewOrderModel, BindingResult result) {
//		if(result.hasErrors()) {
//			return new ModelAndView("review-order");
//		}
//		ReviewOrder reviewOrder = new ReviewOrder();
//		BeanUtils.copyProperties(reviewOrderModel, reviewOrder);
//		reviewOrderService.save(reviewOrder);
//		return new ModelAndView("home");
//	}
//	@GetMapping("/product/{slug}")
//	public String reviewProduct(Model model, @PathVariable("slug") String slugProduct, HttpSession session) {
//		User user = (User)session.getAttribute("user");
//		Optional<Product> optProduct = productService.findBySlug(slugProduct);
//		if(optProduct.isPresent()) {
//			Product product = optProduct.get();
//			Store store = product.getStore();
//			ReviewProduct reviewProduct = new ReviewProduct();
//			reviewProduct.setUser(user);
//			reviewProduct.setProduct(product);
//			reviewProduct.setStore(store);
//			model.addAttribute("reviewProduct",reviewProduct);
//		}
//		return "review-product";
//	}
//	@PostMapping("/reviewed-product")
//	public ModelAndView reviewedOrder(ModelMap model,@Valid @ModelAttribute("reviewProduct") ReviewProduct reviewProductModel, BindingResult result) {
//		if(result.hasErrors()) {
//			return new ModelAndView("review-product");
//		}
//		ReviewProduct reviewProduct = new ReviewProduct();
//		BeanUtils.copyProperties(reviewProductModel, reviewProduct);
//		reviewProductService.save(reviewProduct);
//		return new ModelAndView("home");
//	}
//}
