package com.example.eCommerceApp.controller;

import com.example.eCommerceApp.dto.RequestDto.SellerRequestDto;
import com.example.eCommerceApp.dto.ResponseDto.SellerResponseDto;
import com.example.eCommerceApp.exception.EmailAlreadyPresentException;
import com.example.eCommerceApp.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @PostMapping("/add")
    public SellerResponseDto addSeller(@RequestBody SellerRequestDto sellerRequestDto) throws EmailAlreadyPresentException {

        return sellerService.addSeller(sellerRequestDto);
    }

}
