package vn.iotstar.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminDoanhThuController {
    @GetMapping("/doanhthu")
    public String index() {
        return "admin/doanhthu";
    }
}
