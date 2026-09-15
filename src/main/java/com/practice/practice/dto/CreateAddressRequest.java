package com.practice.practice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data 
public class CreateAddressRequest {
    @NotBlank(message = "country is required")
    private String country;
    @NotBlank(message = "city is required")
    private String city;
    private String postalCode;

}
