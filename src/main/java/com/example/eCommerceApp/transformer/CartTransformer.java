package com.example.eCommerceApp.transformer;

import com.example.eCommerceApp.dto.ResponseDto.CartResponseDto;
import com.example.eCommerceApp.model.Cart;

public class CartTransformer {

    public static CartResponseDto CartToCartResponseDto(Cart cart){

        return CartResponseDto.builder()
                .cartTotal(cart.getCartTotal())
                .customerName(cart.getCustomer().getName())
                .numberOfItems(cart.getNumberOfItems())
                .build();
    }

}
