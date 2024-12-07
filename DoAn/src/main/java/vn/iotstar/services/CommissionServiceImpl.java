package vn.iotstar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.Commission;
import vn.iotstar.entity.Store;
import vn.iotstar.entity.User;
import vn.iotstar.repository.CommissionRepository;
import vn.iotstar.repository.StoreRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommissionServiceImpl {

    @Autowired
    private CommissionRepository commissionRepository;

    @Autowired
    private StoreRepository storeRepository;

    // Method to add a Commission and associated Store to the database
    public void addCommissionWithStore() {
        // Create a Commission entity
        Commission commission = new Commission();
        commission.setName("Premium Commission");
        commission.setCost(5.0f); // 5% cost
        commission.setDescription("This commission plan is for high-performance stores.");
        commission.setDelete(false);
        commission.setCreateAt(new Date(System.currentTimeMillis()));
        commission.setUpdateAt(new Date(System.currentTimeMillis()));

        // Save the Commission entity to the database
        commission = commissionRepository.save(commission);

        // Create a Store entity and associate it with the Commission
        Store store = new Store();
        store.setName("Tech Store");
        store.setBio("Your one-stop shop for all tech gadgets.");
        store.setSlug("tech-store");
        store.setAddress("123 Tech Street, Silicon Valley");
        store.setImages("techstore.jpg");
        store.setActive(true);
        store.setOpen(true);
        store.setPoint(4.5f);
        store.setRating(4.7f);
        store.setCreateAt(new Date(System.currentTimeMillis()));
        store.setUpdateAt(new Date(System.currentTimeMillis()));
        store.setCommission(commission);

        // Create a User (owner of the store)
        User owner = new User();
        owner.setFullname("John Doe");
        store.setOwner(owner);

        // Add the store to the commission's store list
        List<Store> stores = new ArrayList<>();
        stores.add(store);
        commission.setStores(stores);

        // Save the Store entity to the database
        storeRepository.save(store);

        System.out.println("Commission and Store have been saved to the database.");
    }
}
