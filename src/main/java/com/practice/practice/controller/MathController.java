package com.practice.practice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.practice.practice.service.MathService;


@RestController
public class MathController {

    private final MathService mathService;  

    public MathController(MathService mathService){
        this.mathService=mathService;
    }

    @GetMapping("/add/{a}/{b}")
    public String add(@PathVariable int a, @PathVariable int b) {
        int result = mathService.addNumbers(a, b);
        return "this is the answer "+result;
    }

    
}
