package vn.iotstar.services;

import vn.iotstar.entity.Store;
import java.util.List;
import java.util.Optional;

public interface StoreService {
    List<Store> findAll();
    Optional<Store> findById(Long id);
    void save(Store store);
    void deleteById(Long id);
}
