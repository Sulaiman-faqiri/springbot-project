package com.practice.practice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.practice.practice.dto.CreateAddressRequest;
import com.practice.practice.dto.UpdateAddressRequest;
import com.practice.practice.model.Address;
import com.practice.practice.service.AddressService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping("/addresses")
public class AddressController {
    private AddressService addressService;

    @GetMapping
    public List<Address> index() {
        return addressService.getAllAddresses();
    }

    @PostMapping
    public ResponseEntity<Address> create(@RequestBody @Valid CreateAddressRequest request) {
        return ResponseEntity.status(201).body(addressService.createAddress(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> update(Long id, @RequestBody @Valid UpdateAddressRequest request) {
        return ResponseEntity.status(201).body(addressService.updateAddress(id, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        addressService.deleteAddress(id);
    }

}
