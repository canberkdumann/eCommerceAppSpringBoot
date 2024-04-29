package com.example.eCommerceApp.transformer;

import com.example.eCommerceApp.dto.ResponseDto.OrderResponseDto;
import com.example.eCommerceApp.model.Ordered;

public class OrderTransformer {

    public static OrderResponseDto OrderToOrderResponseDto(Ordered ordered){

        return OrderResponseDto.builder()
                .orderNo(ordered.getOrderNo())
                .totalValue(ordered.getTotalValue())
                .orderDate(ordered.getOrderDate())
                .customerName(ordered.getCustomer().getName())
                .build();
    }
}
