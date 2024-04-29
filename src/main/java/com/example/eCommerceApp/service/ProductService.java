package com.example.eCommerceApp.service;

import com.example.eCommerceApp.Enum.ProductCategory;
import com.example.eCommerceApp.Enum.ProductStatus;
import com.example.eCommerceApp.dto.RequestDto.ProductRequestDto;
import com.example.eCommerceApp.dto.RequestDto.UpdateProductRequestDto;
import com.example.eCommerceApp.dto.ResponseDto.ProductResponseDto;
import com.example.eCommerceApp.exception.InvalidProductException;
import com.example.eCommerceApp.exception.InvalidSellerException;
import com.example.eCommerceApp.model.Item;
import com.example.eCommerceApp.model.Product;
import com.example.eCommerceApp.model.Seller;
import com.example.eCommerceApp.repository.ProductRepository;
import com.example.eCommerceApp.repository.SellerRepository;
import com.example.eCommerceApp.transformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.eCommerceApp.Enum.ProductStatus.AVAILABLE;
import static com.example.eCommerceApp.Enum.ProductStatus.OUT_OF_STOCK;

@Service
public class ProductService {

    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    private ProductRepository productRepository;

    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws InvalidSellerException {

        Seller seller;
        try {
            seller = sellerRepository.findById(productRequestDto.getSellerId()).get();
        } catch (Exception e) {
            throw new InvalidSellerException("Seller doesn't exist");
        }

        Product product = ProductTransformer.ProductRequestDtoToProduct(productRequestDto);
        product.setSeller(seller);

        seller.getProducts().add(product);
        sellerRepository.save(seller);

        return ProductTransformer.ProductToProductResponseDto(product);
    }

    public ProductResponseDto updateProduct(UpdateProductRequestDto updateProductRequestDto) throws InvalidProductException {

        Product product;
        try {
            product = productRepository.findById(updateProductRequestDto.getProductId()).get();
        } catch (Exception e) {
            throw new InvalidProductException("Product not found!!");
        }

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setPrice(updateProductRequestDto.getPrice());
        productResponseDto.setQuantity(updateProductRequestDto.getQuantity());
        productResponseDto.setProductName(product.getName());
        productResponseDto.setSellerName(product.getSeller().getName());
        productResponseDto.setProductStatus(product.getProductStatus());

        return productResponseDto;
    }

    public List<ProductResponseDto> getAllProductsByCategory(ProductCategory category) {

        List<Product> products = productRepository.findByProductCategory(category);

        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for (Product product : products) {
            productResponseDtos.add(ProductTransformer.ProductToProductResponseDto(product));
        }

        return productResponseDtos;
    }

    public String deleteProduct(int sellerId, int productId) throws InvalidSellerException {

        Seller seller;
        try {
            seller = sellerRepository.findById(sellerId).get();
        } catch (Exception e) {
            throw new InvalidSellerException("Seller doesn't exist!!");
        }

        List<Product> products = seller.getProducts();
        for(Product product: products){
            if(product.getId() == productId){
                productRepository.delete(product);
            }
        }
        sellerRepository.save(seller);

        return ("Product deleted successfully!");
    }


    public List<ProductResponseDto> outOfStockProducts() {

        List<Product> products = productRepository.findByProductStatus(OUT_OF_STOCK);

        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

        for (Product product : products) {
            productResponseDtos.add(ProductTransformer.ProductToProductResponseDto(product));
        }

        return productResponseDtos;
    }

    public List<ProductResponseDto> availableProducts() {

        List<Product> products = productRepository.findByProductStatus(AVAILABLE);

        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

        for (Product product : products) {
            productResponseDtos.add(ProductTransformer.ProductToProductResponseDto(product));
        }

        return productResponseDtos;
    }

    public void decreaseProductQuantity(Item item) throws Exception {

        Product product = item.getProduct();
        int quantity = item.getRequiredQuantity();
        int currentQuantity = product.getQuantity();
        if(quantity > currentQuantity){
            throw new Exception("Out of stock");
        }
        product.setQuantity(currentQuantity - quantity);
        if(product.getQuantity() == 0){
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        }
    }


}
