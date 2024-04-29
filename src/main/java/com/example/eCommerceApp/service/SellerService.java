package com.example.eCommerceApp.service;

import com.example.eCommerceApp.dto.RequestDto.SellerRequestDto;
import com.example.eCommerceApp.dto.ResponseDto.SellerResponseDto;
import com.example.eCommerceApp.exception.EmailAlreadyPresentException;
import com.example.eCommerceApp.exception.InvalidSellerException;
import com.example.eCommerceApp.model.Seller;
import com.example.eCommerceApp.repository.SellerRepository;
import com.example.eCommerceApp.transformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;

    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) throws EmailAlreadyPresentException {

         if(sellerRepository.findByEmailId(sellerRequestDto.getEmailId())!=null)
             throw new EmailAlreadyPresentException("Email Id already registered");

        Seller seller = SellerTransformer.SellerRequestDtoToSeller(sellerRequestDto);
        Seller savedSeller = sellerRepository.save(seller);

        SellerResponseDto sellerResponseDto = SellerTransformer.SellerToSellerResponseDto(savedSeller);
        return sellerResponseDto;

    }
}
