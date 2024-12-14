package vn.iotstar.controllers.customer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.iotstar.entity.User;
import vn.iotstar.services.UserService;
import vn.iotstar.utils.Constain;

@Controller
@RequestMapping("/profile")
public class CustomerProfileController {
	@Autowired
	UserService userService;

	@RequestMapping("")
	public String profile(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		return "customer/user-profile";
	}

	@GetMapping("/map")
	public String Map(Model model) {
		return "customer/layToaDo";
	}

	@PostMapping("/update")
	public ModelAndView editProfile(ModelMap model, @Valid @ModelAttribute("user") User userModel,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("profile");
		}
		User user = new User();
		BeanUtils.copyProperties(userModel, user);
		Optional<User> optuser = userService.findById(user.getId());
		User userold = optuser.get();
		user.setPassword(userold.getPassword());
		userService.save(user);
		return new ModelAndView("redirect:/profile", model);
	}
	
	
	@PostMapping("/update/test")
	public ModelAndView editTest(ModelMap model, @Valid @ModelAttribute("user") User userModel,
	                              BindingResult result, @RequestParam("avatar") MultipartFile file) throws IOException {

	    User user = new User();
	    BeanUtils.copyProperties(userModel, user);

	    Optional<User> optuser = userService.findById(user.getId());
	    if (!optuser.isPresent()) {
	        return new ModelAndView("redirect:/profile", model);
	    }

	    User userold = optuser.get();
	    user.setPassword(userold.getPassword());

	    // Xử lý ảnh avatar
	    String avatarFilename = userold.getAvatar();  // Giữ lại tên avatar cũ nếu không có file mới
	    String avatarFullPath = userold.getAvatar();  // Giữ lại đường dẫn cũ

	    if (file != null && !file.isEmpty()) {
	        String filename = file.getOriginalFilename();
	        String extension = filename.substring(filename.lastIndexOf("."));
	        
	        avatarFilename = generateNewFileName(extension);

	        String uploadDir = Constain.UPLOAD_DIRECTORY;
	        // Đảm bảo đường dẫn chứa dấu "/"
	        uploadDir = uploadDir.replace("\\", "/");  // Thay thế \ thành /

	        File uploadDirectory = new File(uploadDir);
	        if (!uploadDirectory.exists()) {
	            uploadDirectory.mkdirs(); 
	        }

	        avatarFullPath = uploadDir + "/" + avatarFilename;  // Dùng "/" thay vì File.separator

	        // Lưu file vào thư mục đã chỉ định
	        file.transferTo(new File(avatarFullPath));
	    }
	    
	    // Lưu thông tin của người dùng (chưa bao gồm phần lưu vào DB)
	    user.setAvatar(avatarFullPath);
	    userService.save(user);

	    // Chuyển hướng hoặc trả về view
	    model.addAttribute("user", user);
	    return new ModelAndView("redirect:/profile", model);
	}

	
	private String generateNewFileName(String extension) {
	    // Lấy giây từ thời gian hiện tại
	    long seconds = System.currentTimeMillis() / 1000;
	    Random random = new Random();
	    int randomNumber = 1000 + random.nextInt(9000); // Random từ 1000 đến 9999

	    return "avatar_" + randomNumber + "_" + seconds + extension;
	}

	@PostMapping("/update/map")
	public ModelAndView editMap(HttpSession session, ModelMap model, @Validated @ModelAttribute("user") User userModel,
			BindingResult result) {
		
		if (result.hasErrors()) {
			return new ModelAndView("profile");
		}

		User sessionUser = (User) session.getAttribute("user");
		if (sessionUser == null) {
			model.addAttribute("error", "User not logged in or session expired");
			return new ModelAndView("redirect:/login");
		}

		User user = new User();
		BeanUtils.copyProperties(sessionUser, user);

		Optional<User> optuser = userService.findById(user.getId());
		if (!optuser.isPresent()) {
			model.addAttribute("error", "User not found");
			return new ModelAndView("profile", model);
		}

		User userold = optuser.get();
		user.setPassword(userold.getPassword());

		user.setLatitude(userModel.getLatitude());
		user.setLongitude(userModel.getLongitude()); 
		user.setAddress(userModel.getAddress());

		userService.save(user);

		session.setAttribute("user", user);

		return new ModelAndView("redirect:/profile", model);
	}

}
