package com.example.eCommerceApp.dto.ResponseDto;

import com.example.eCommerceApp.Enum.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
public class ProductResponseDto {

    String productName;

    String sellerName;

    int quantity;

    int price;

    ProductStatus productStatus;


}
