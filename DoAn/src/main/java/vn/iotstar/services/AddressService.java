package vn.iotstar.services;

import java.util.List;

import org.springframework.data.domain.Sort;

import vn.iotstar.entity.Address;

public interface AddressService {

	List<Address> findAll();

	List<Address> findAll(Sort sort);

	<S extends Address> S save(S entity);

}
