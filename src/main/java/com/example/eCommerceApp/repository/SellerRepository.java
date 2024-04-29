package com.example.eCommerceApp.repository;

import com.example.eCommerceApp.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer> {

    Seller findByEmailId(String email);

}
