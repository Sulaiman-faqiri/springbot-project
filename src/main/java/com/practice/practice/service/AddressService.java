package com.practice.practice.service;

import java.util.List;

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

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
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
