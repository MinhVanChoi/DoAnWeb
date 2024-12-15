package vn.iotstar.services.implement;

import org.springframework.stereotype.Service;
import vn.iotstar.entity.Store;
import vn.iotstar.repository.StoreRepository;
import vn.iotstar.services.StoreService;

import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    @Override
    public Optional<Store> findById(Long id) {
        return storeRepository.findById(id);
    }

    @Override
    public void save(Store store) {
        storeRepository.save(store);
    }

    @Override
    public void deleteById(Long id) {
        storeRepository.deleteById(id);
    }
}
