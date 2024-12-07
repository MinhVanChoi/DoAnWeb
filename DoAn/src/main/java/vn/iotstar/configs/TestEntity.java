package vn.iotstar.configs;

import vn.iotstar.entity.Commission;
import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;
import vn.iotstar.services.CommissionServiceImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestEntity {

	@Autowired
    private CommissionServiceImpl commissionService;

    @GetMapping("/addCommission")
    public String addCommission() {
        // Call the service method to add Commission and Store to the database
        commissionService.addCommissionWithStore();
        return "Commission and Store have been added to the database.";
    }
}
