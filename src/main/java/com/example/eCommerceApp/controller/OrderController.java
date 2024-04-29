package com.example.eCommerceApp.controller;

import com.example.eCommerceApp.dto.RequestDto.OrderRequestDto;
import com.example.eCommerceApp.dto.ResponseDto.OrderResponseDto;
import com.example.eCommerceApp.exception.InvalidCustomerException;
import com.example.eCommerceApp.repository.OrderedRepository;
import com.example.eCommerceApp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderedRepository orderedRepository;

    @PostMapping("/place")
    public OrderResponseDto placeDirectOrder(@RequestBody OrderRequestDto orderRequestDto) throws Exception {

        return orderService.placeOrder(orderRequestDto);
    }

    @GetMapping("/get_all")
    public List<OrderResponseDto> getAllOrders(@RequestParam("customerId") int customerId) throws InvalidCustomerException {

        return orderService.getAllOrders(customerId);
    }

    @DeleteMapping("/delete")
    public String deleteOrder(@RequestParam("customerId") int customerId, @RequestParam("orderNo") String orderNo) throws InvalidCustomerException {

        return orderService.deleteOrder(customerId, orderNo);
    }

}
