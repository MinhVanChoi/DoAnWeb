package vn.iotstar.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import vn.iotstar.entity.Product;
import vn.iotstar.entity.Store;
import vn.iotstar.services.ProductService;
import vn.iotstar.services.StoreService;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
    @Autowired
    ProductService productService;

    private final StoreService storeService;
    public AdminProductController(StoreService storeService)
    { this.storeService = storeService; }

    private Page<Product> getPaginatedResult(Optional<Integer> page, Optional<Integer> size, String nameFilter) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));

        if (StringUtils.hasText(nameFilter)) {
            return productService.findByNameContaining(nameFilter, pageable);
        } else {
            return productService.findAll(pageable);
        }
    }

    @RequestMapping("")
    public String all(Model model, @RequestParam("page") Optional<Integer> page,
                      @RequestParam("size") Optional<Integer> size) {

        Page<Product> resultPage = getPaginatedResult(page, size, null);

        int totalPages = resultPage.getTotalPages();
        List<Integer> pageNumbers = new ArrayList<>();
        int currentPage = resultPage.getNumber() + 1;

        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
            for (int i = start; i <= end; i++) {
                pageNumbers.add(i);
            }
        }

        model.addAttribute("productPage", resultPage);
        model.addAttribute("pageNumbers", pageNumbers);
        return "admin/product-list";
    }



    @GetMapping("/{slug}")
    public ModelAndView viewProduct(ModelMap model, @PathVariable("slug") String slugProduct) {
        Optional<Product> optProduct = productService.findBySlug(slugProduct);
        if (optProduct.isPresent()) {
            Product product = optProduct.get();
            model.addAttribute("product", product);
            return new ModelAndView("admin/profile-product", model);
        }
        return new ModelAndView("forward:/admin/products");
    }

    @PostMapping("/ban")
    public ModelAndView banProduct(ModelMap model, @PathVariable("slug") String slugProduct) {
        Optional<Product> optProduct = productService.findBySlug(slugProduct);
        if (optProduct.isPresent()) {
            Product product = optProduct.get();
            productService.save(product);
            model.addAttribute("product", product);
            return new ModelAndView("admin/profile-product", model);
        }
        return new ModelAndView("forward:/admin/products");
    }


    @PostMapping("/unban/{slug}")
    public ModelAndView unbanProduct(ModelMap model, @PathVariable("slug") String slugProduct) {
        Optional<Product> optProduct = productService.findBySlug(slugProduct);
        if (optProduct.isPresent()) {
            Product product = optProduct.get();

            model.addAttribute("product", product);
            productService.save(product);
            return new ModelAndView("admin/profile-product", model);
        }
        return new ModelAndView("forward:/admin/products");
    }


    @RequestMapping("/searchpaginated")
    public String search(ModelMap model,
                         @RequestParam(name = "name", required = false) String name,
                         @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size) {

        // Use the helper method to get the paginated result
        Page<Product> resultPage = getPaginatedResult(page, size, name);

        // Handle pagination display
        int totalPages = resultPage.getTotalPages();
        List<Integer> pageNumbers = new ArrayList<>();
        int currentPage = resultPage.getNumber() + 1;  // Get the current page (1-based)

        // Generate page numbers (currentPage ± 2 range)
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
            for (int i = start; i <= end; i++) {
                pageNumbers.add(i);
            }
        }

        model.addAttribute("productPage", resultPage);  // Add paginated result to the model
        model.addAttribute("pageNumbers", pageNumbers);    // Add page numbers for pagination
        model.addAttribute("name", name);  // Add 'name' parameter for filtering
        return "admin/product-list";  // Return the view
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<Store> store = storeService.findAll();
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("store", store);
        return "/admin/add";
    }

    @PostMapping("/insert")
    public ModelAndView insert(ModelMap model, @Valid @ModelAttribute("product") Product product,
                               BindingResult result,@RequestParam("storeId") Long storeId, @RequestParam("images") MultipartFile file) throws IOException {


        String imagesFullPath = product.getImages();  // Giữ lại đường dẫn cũ
        if (file != null && !file.isEmpty()) {
            String filename = file.getOriginalFilename();
            String extension = filename.substring(filename.lastIndexOf("."));

            String imagesFilename = generateNewFileName(extension);

            String uploadDir = "D:";
            // Đảm bảo đường dẫn chứa dấu "/"
            uploadDir = uploadDir.replace("\\", "/");  // Thay thế \ thành /

            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            imagesFullPath = uploadDir + "/" + imagesFilename;  // Dùng "/" thay vì File.separator

            // Lưu file vào thư mục đã chỉ định
            file.transferTo(new File(imagesFullPath));

        }
        Store store = storeService.findById(storeId).orElseThrow(() -> new IllegalArgumentException("Invalid store Id:" + storeId));
        product.setStore(store);
        product.setImages(imagesFullPath);
        productService.save(product);
        return new ModelAndView("redirect:/admin/products", model);
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        List<Store> store = storeService.findAll();
        Optional<Product> optionalProduct = productService.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            model.addAttribute("product", product);
            model.addAttribute("store", store);
            return "admin/edit";
        } else {
            return "redirect:/admin/products";
        }
    }


    @PostMapping("/update")
    public ModelAndView update(ModelMap model, @Valid @ModelAttribute("product") Product product,
                               BindingResult result,@RequestParam("storeId") Long storeId, @RequestParam("images") MultipartFile file) throws IOException {

        String imagesFullPath = product.getImages();  // Giữ lại đường dẫn cũ
        if (file != null && !file.isEmpty()) {
            String filename = file.getOriginalFilename();
            String extension = filename.substring(filename.lastIndexOf("."));

            String imagesFilename = generateNewFileName(extension);

            String uploadDir = "D:";
            // Đảm bảo đường dẫn chứa dấu "/"
            uploadDir = uploadDir.replace("\\", "/");  // Thay thế \ thành /

            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            imagesFullPath = uploadDir + "/" + imagesFilename;  // Dùng "/" thay vì File.separator

            // Lưu file vào thư mục đã chỉ định
            file.transferTo(new File(imagesFullPath));

        }
        Store store = storeService.findById(storeId).orElseThrow(() -> new IllegalArgumentException("Invalid store Id:" + storeId));
        product.setStore(store);
        product.setImages(imagesFullPath);
        productService.save(product);
        return new ModelAndView("redirect:/admin/products", model);
    }


    private String generateNewFileName(String extension) {

        long seconds = System.currentTimeMillis() / 1000;
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000); // Random từ 1000 đến 9999
        return "avatar_" + randomNumber + "_" + seconds + extension;
    }

}