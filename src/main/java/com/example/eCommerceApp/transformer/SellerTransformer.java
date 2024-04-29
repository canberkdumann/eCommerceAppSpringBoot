package com.example.eCommerceApp.transformer;

import com.example.eCommerceApp.dto.RequestDto.SellerRequestDto;
import com.example.eCommerceApp.dto.ResponseDto.SellerResponseDto;
import com.example.eCommerceApp.model.Seller;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SellerTransformer {

    public static Seller SellerRequestDtoToSeller(SellerRequestDto sellerRequestDto){

        return Seller.builder()
                .name(sellerRequestDto.getName())
                .age(sellerRequestDto.getAge())
                .emailId(sellerRequestDto.getEmailId())
                .mobNo(sellerRequestDto.getMobNo())
                .build();
    }

    public static SellerResponseDto SellerToSellerResponseDto(Seller seller){

        return SellerResponseDto.builder()
                .name(seller.getName())
                .age(seller.getAge())
                .mobile(seller.getMobNo())
                .build();
    }
}
