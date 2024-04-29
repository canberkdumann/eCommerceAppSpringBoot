package com.example.eCommerceApp.transformer;

import com.example.eCommerceApp.dto.RequestDto.CustomerRequestDto;
import com.example.eCommerceApp.dto.ResponseDto.CustomerResponseDto;
import com.example.eCommerceApp.model.Customer;

public class CustomerTransformer {

    public static Customer CustomerRequestDtoToCustomer(CustomerRequestDto customerRequestDto){


        return Customer.builder()
                .age(customerRequestDto.getAge())
                .name(customerRequestDto.getName())
                .address(customerRequestDto.getAddress())
                .emailId(customerRequestDto.getEmailId())
                .mobNo(customerRequestDto.getMobNo())
                .build();
    }

    public static CustomerResponseDto CustomerToCustomerResponseDto(Customer customer){

        return CustomerResponseDto.builder()
                .name(customer.getName())
                .message("Welcome " + customer.getName()+ " to E Commerce App !!")
                .build();
    }
}