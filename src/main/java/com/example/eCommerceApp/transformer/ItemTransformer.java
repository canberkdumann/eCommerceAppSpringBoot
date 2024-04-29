package com.example.eCommerceApp.transformer;

import com.example.eCommerceApp.dto.RequestDto.ItemRequestDto;
import com.example.eCommerceApp.dto.ResponseDto.ItemResponseDto;
import com.example.eCommerceApp.model.Item;

public class ItemTransformer {

    public static Item ItemRequestDtoToItem(ItemRequestDto itemRequestDto){

        return Item.builder()
                .requiredQuantity(itemRequestDto.getRequiredQuantity())
                .build();
    }

    public static ItemResponseDto ItemToItemResponseDto(Item item){

        return ItemResponseDto.builder()
                .priceOfOneItem(item.getProduct().getPrice())
                .totalPrice(item.getRequiredQuantity() * item.getProduct().getPrice())
                .productName(item.getProduct().getName())
                .quantity(item.getRequiredQuantity())
                .build();
    }
}
