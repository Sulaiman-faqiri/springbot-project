package com.practice.practice.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.practice.practice.dto.CreateAddressRequest;
import com.practice.practice.dto.UpdateAddressRequest;
import com.practice.practice.exception.ResourceNotFoundException;
import com.practice.practice.model.Address;
import com.practice.practice.repository.AddressRespository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AddressService {
    private final AddressRespository addressRepository;

    public Page<Address> getAllAddresses(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    public Address createAddress(CreateAddressRequest request) {
        Address address = new Address(request.getCountry(), request.getCity(), request.getPostalCode());
        return addressRepository.save(address);
    }

    public Address updateAddress(Long id, UpdateAddressRequest request) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("address", id));
        address.setCountry(request.getCountry());
        address.setCity(request.getCity());
        address.setPostalCode(request.getPostalCode());

        return addressRepository.save(address);

    }

    public void deleteAddress(Long id) {
        addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("address", id));
        addressRepository.deleteById(id);
    }

}
