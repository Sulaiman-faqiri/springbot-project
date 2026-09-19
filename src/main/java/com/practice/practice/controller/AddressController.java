package com.practice.practice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.practice.practice.dto.CreateAddressRequest;
import com.practice.practice.dto.UpdateAddressRequest;
import com.practice.practice.model.Address;
import com.practice.practice.service.AddressService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public Page<Address> index(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return addressService.getAllAddresses(pageable);
    }

    @PostMapping
    public ResponseEntity<Address> create(@RequestBody @Valid CreateAddressRequest request) {
        return ResponseEntity.status(201).body(addressService.createAddress(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> update(@PathVariable Long id, @RequestBody @Valid UpdateAddressRequest request) {
        return ResponseEntity.ok(addressService.updateAddress(id, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        addressService.deleteAddress(id);
    }

}
