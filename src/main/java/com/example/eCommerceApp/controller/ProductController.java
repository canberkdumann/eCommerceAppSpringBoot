package com.example.eCommerceApp.controller;

import com.example.eCommerceApp.Enum.ProductCategory;
import com.example.eCommerceApp.dto.RequestDto.ProductRequestDto;
import com.example.eCommerceApp.dto.RequestDto.UpdateProductRequestDto;
import com.example.eCommerceApp.dto.ResponseDto.ProductResponseDto;
import com.example.eCommerceApp.exception.InvalidProductException;
import com.example.eCommerceApp.exception.InvalidSellerException;
import com.example.eCommerceApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ProductResponseDto addProduct(@RequestBody ProductRequestDto productRequestDto) throws InvalidSellerException {

        return productService.addProduct(productRequestDto);
    }

    @PutMapping("/update")
    public ProductResponseDto updateProduct(@RequestBody UpdateProductRequestDto updateProductRequestDto) throws InvalidProductException {

        return productService.updateProduct(updateProductRequestDto);
    }

    @GetMapping("/get/{category}")
    public List<ProductResponseDto> getAllProductsByCategory(@PathVariable("category") ProductCategory category){

        return productService.getAllProductsByCategory(category);
    }

    @DeleteMapping("/delete")
    public String deleteProduct(@RequestParam("sellerId") int sellerId, @RequestParam("productId") int productId) throws InvalidSellerException {

        return productService.deleteProduct(sellerId, productId);
    }

    @GetMapping("/out_of_stock")
    public List<ProductResponseDto> outOfStockProducts(){

        return productService.outOfStockProducts();
    }

    @GetMapping("/available")
    public List<ProductResponseDto> availableProducts(){

        return productService.availableProducts();
    }

}
