package vn.iotstar.controllers.customer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;
import vn.iotstar.entity.UserFollowStore;
import vn.iotstar.entity.UserFollowStoreId;
import vn.iotstar.services.StoreService;
import vn.iotstar.services.UserFollowStoreService;

@Controller
@RequestMapping("/follow-store")
public class CustomerFollowStoreController {
	@Autowired
	StoreService storeService;
	@Autowired
	UserFollowStoreService userFollowStoreService;
	@RequestMapping("")
	public String all(Model model) {
		return "user/follow-store";
	}
	@GetMapping("/follow/{slug}")
	public ModelAndView followStore(ModelMap model, @PathVariable("slug") String slugStore, HttpSession session) {
		User user = (User)session.getAttribute("user");
		Optional<Store> optStore = storeService.findBySlug(slugStore);
		if(optStore.isPresent()) {
			Store store = optStore.get();
			UserFollowStore userFollowStore = new UserFollowStore();
			userFollowStore.setUser(user);
			userFollowStore.setStore(store);
			userFollowStoreService.save(userFollowStore);
		}
		//sua lai thanh trang dang xem
		return new ModelAndView("forward:/follow-store");
	}
	@GetMapping("/unfollow/{slug}")
	public ModelAndView unfollowStore(ModelMap model, @PathVariable("slug") String slugStore, HttpSession session) {
		User user = (User)session.getAttribute("user");
		Optional<Store> optStore = storeService.findBySlug(slugStore);
		if(optStore.isPresent()) {
			Store store = optStore.get();
			UserFollowStoreId userFollowStoreId = new UserFollowStoreId(user.getId(), store.getId());
			Optional<UserFollowStore> optUserFollowStore = userFollowStoreService.findById(userFollowStoreId);
			if(optUserFollowStore.isPresent()) {
				userFollowStoreService.deleteById(userFollowStoreId);
			}
		}
		//sua lai thanh trang dang xem
		return new ModelAndView("forward:/follow-store");
	}
}
