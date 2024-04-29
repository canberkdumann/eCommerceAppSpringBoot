package com.example.eCommerceApp.transformer;

import com.example.eCommerceApp.Enum.ProductStatus;
import com.example.eCommerceApp.dto.RequestDto.ProductRequestDto;
import com.example.eCommerceApp.dto.ResponseDto.ProductResponseDto;
import com.example.eCommerceApp.model.Product;

public class ProductTransformer {

    public static Product ProductRequestDtoToProduct(ProductRequestDto productRequestDto){

        return Product.builder()
                .name(productRequestDto.getProductName())
                .price(productRequestDto.getPrice())
                .productCategory(productRequestDto.getProductCategory())
                .quantity(productRequestDto.getQuantity())
                .productStatus(ProductStatus.AVAILABLE)
                .build();
    }

    public static ProductResponseDto ProductToProductResponseDto(Product product){
        return ProductResponseDto.builder()
                .productName(product.getName())
                .sellerName(product.getSeller().getName())
                .quantity(product.getQuantity())
                .productStatus(product.getProductStatus())
                .price(product.getPrice())
                .build();
    }
}
