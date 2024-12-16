package vn.iotstar.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.Store;
import vn.iotstar.services.StoreService;

import java.util.Optional;

@Controller
@RequestMapping("/admin/stores")
public class AdminStoreController {

    private final StoreService storeService;

    @Autowired
    public AdminStoreController(StoreService storeService)
    { this.storeService = storeService; }


    @RequestMapping("")
    public String listStores(Model model) {
        model.addAttribute("store", storeService.findAll());
        return "admin/store-list";
    }

    @GetMapping("/add")
    public String showAddStoreForm(Model model) {
        model.addAttribute("store", new Store());
        return "admin/store-add"; // Tên của form thêm/sửa
    }

    @PostMapping("/insert")
    public String addStore(@ModelAttribute("store") Store store) {
        storeService.save(store);
        return "redirect:/admin/stores";
    }

    @GetMapping("/edit/{id}")
    public String showEditStoreForm(@PathVariable Long id, Model model) {
        Optional<Store> optionalStore = storeService.findById(id);
        if (optionalStore.isPresent()) {
            Store store= optionalStore.get();
            model.addAttribute("store", store);
            return "admin/store-edit";
        } else {
            // Xử lý trường hợp không tìm thấy sản phẩm
            return "redirect:/admin/stores";
        }
    }

    @PostMapping("/update")
    public String editStore(@ModelAttribute("store") Store store) {
        storeService.save(store);
        return "redirect:/admin/stores";
    }

    @GetMapping("/delete/{id}")
    public String deleteStore(@PathVariable Long id) {
        storeService.deleteById(id);
        return "redirect:/admin/stores";
    }
}
