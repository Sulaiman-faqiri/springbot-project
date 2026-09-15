package com.practice.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.practice.model.Address;

public interface AddressRespository extends JpaRepository<Address, Long> {

}
