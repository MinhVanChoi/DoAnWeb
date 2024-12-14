package vn.iotstar.services.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Address;
import vn.iotstar.repository.AddressRepository;
import vn.iotstar.services.AddressService;

@Service
public class AddressServiceImp implements AddressService {

	
	@Autowired
	AddressRepository addressRepository;
	
	@Override
	public <S extends Address> S save(S entity) {
		return addressRepository.save(entity);
	}

	@Override
	public List<Address> findAll(Sort sort) {
		return addressRepository.findAll(sort);
	}
	
	@Override
	public List<Address> findAll() {
		return addressRepository.findAll();
	}
	
	
}
