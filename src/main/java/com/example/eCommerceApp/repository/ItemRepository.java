package com.example.eCommerceApp.repository;

import com.example.eCommerceApp.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
