package com.example.eCommerceApp.repository;

import com.example.eCommerceApp.Enum.ProductCategory;
import com.example.eCommerceApp.Enum.ProductStatus;
import com.example.eCommerceApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findByProductCategory(ProductCategory productCategory);

    List<Product> findByProductStatus(ProductStatus productStatus);

}
