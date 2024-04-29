package com.example.eCommerceApp.controller;

import com.example.eCommerceApp.dto.RequestDto.CustomerRequestDto;
import com.example.eCommerceApp.dto.ResponseDto.CustomerResponseDto;
import com.example.eCommerceApp.exception.MobileNoAlreadyPresentException;
import com.example.eCommerceApp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/add")
    public CustomerResponseDto addCustomer(@RequestBody CustomerRequestDto customerRequestDto) throws MobileNoAlreadyPresentException {

        return customerService.addCustomer(customerRequestDto);
    }

}
