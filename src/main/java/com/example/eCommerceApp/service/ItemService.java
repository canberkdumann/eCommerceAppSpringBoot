package com.example.eCommerceApp.service;

import com.example.eCommerceApp.Enum.ProductStatus;
import com.example.eCommerceApp.dto.RequestDto.ItemRequestDto;
import com.example.eCommerceApp.exception.InvalidCustomerException;
import com.example.eCommerceApp.exception.InvalidProductException;
import com.example.eCommerceApp.model.Cart;
import com.example.eCommerceApp.model.Customer;
import com.example.eCommerceApp.model.Item;
import com.example.eCommerceApp.model.Product;
import com.example.eCommerceApp.repository.CustomerRepository;
import com.example.eCommerceApp.repository.ItemRepository;
import com.example.eCommerceApp.repository.ProductRepository;
import com.example.eCommerceApp.transformer.ItemTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ProductRepository productRepository;

    public Item addItem(ItemRequestDto itemRequestDto) throws Exception {

        Customer customer;
        try{
            customer = customerRepository.findById(itemRequestDto.getCustomerId()).get();
        }
        catch (Exception e){
            throw new InvalidCustomerException("Customer Id is invalid !!");
        }

        Product product;
        try{
            product = productRepository.findById(itemRequestDto.getProductId()).get();
        }
        catch(Exception e){
            throw new InvalidProductException("Product doesn't exist");
        }

        if(itemRequestDto.getRequiredQuantity()>product.getQuantity() || product.getProductStatus()!= ProductStatus.AVAILABLE){
            throw new Exception("Product out of Stock");
        }

        Item item = ItemTransformer.ItemRequestDtoToItem(itemRequestDto);
        item.setCart(customer.getCart());
        item.setProduct(product);

        product.getItemList().add(item);
        return itemRepository.save(item);
    }

    public Item removedItem(ItemRequestDto itemRequestDto) throws Exception {

        Customer customer;
        try{
            customer = customerRepository.findById(itemRequestDto.getCustomerId()).get();
        }
        catch (Exception e){
            throw new InvalidCustomerException("Customer Id is invalid !!");
        }

        Cart currentCart = customer.getCart();
        List<Item> items = currentCart.getItems();

        Product currentProduct = null;

        for(Item item: items){
            if(item.getProduct().getId() == itemRequestDto.getProductId()){
                currentProduct = item.getProduct();
            }
        }

        int enteredQuantity = itemRequestDto.getRequiredQuantity();

        Item item = new Item();
        item.setRequiredQuantity(enteredQuantity);
        item.setProduct(currentProduct);
        item.setCart(currentCart);

        return itemRepository.save(item);
    }

}
