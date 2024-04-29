package com.example.eCommerceApp.service;

import com.example.eCommerceApp.dto.RequestDto.CustomerRequestDto;
import com.example.eCommerceApp.dto.ResponseDto.CustomerResponseDto;
import com.example.eCommerceApp.exception.InvalidCustomerException;
import com.example.eCommerceApp.exception.MobileNoAlreadyPresentException;
import com.example.eCommerceApp.model.Cart;
import com.example.eCommerceApp.model.Customer;
import com.example.eCommerceApp.repository.CustomerRepository;
import com.example.eCommerceApp.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) {

        Customer customer = CustomerTransformer.CustomerRequestDtoToCustomer(customerRequestDto);
        Cart cart = Cart.builder()
                .cartTotal(0)
                .numberOfItems(0)
                .customer(customer)
                .build();
        customer.setCart(cart);

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerTransformer.CustomerToCustomerResponseDto(savedCustomer);
    }
}
