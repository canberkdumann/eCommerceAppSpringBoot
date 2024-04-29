package com.example.eCommerceApp.service;

import com.example.eCommerceApp.dto.ResponseDto.CartResponseDto;
import com.example.eCommerceApp.dto.ResponseDto.ItemResponseDto;
import com.example.eCommerceApp.exception.InvalidCustomerException;
import com.example.eCommerceApp.model.*;
import com.example.eCommerceApp.repository.*;
import com.example.eCommerceApp.transformer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired OrderService orderService;

    @Autowired
    OrderedRepository orderedRepository;

    public CartResponseDto saveCart(Integer customerId, Item item){

        Customer customer = customerRepository.findById(customerId).get();
        Cart cart = customer.getCart();

        int newTotal = cart.getCartTotal() + item.getRequiredQuantity()*item.getProduct().getPrice();
        cart.setCartTotal(newTotal);
        cart.getItems().add(item);

        cart.setNumberOfItems(cart.getItems().size());
        Cart savedCart = cartRepository.save(cart);

        CartResponseDto cartResponseDto = CartTransformer.CartToCartResponseDto(savedCart);

        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();
        for(Item itemEntity: savedCart.getItems()){
            ItemResponseDto itemResponseDto = ItemTransformer.ItemToItemResponseDto(itemEntity);

            itemResponseDtoList.add(itemResponseDto);
        }

        cartResponseDto.setItems(itemResponseDtoList);
        return cartResponseDto;
    }

    public void resetCart(Cart cart){

        cart.setCartTotal(0);
        for(Item item: cart.getItems()){
            item.setCart(null);
        }
        cart.setNumberOfItems(0);
        cart.getItems().clear();

    }

    public CartResponseDto removeFromCart(int customerId, Item item) throws Exception {

        Customer customer = customerRepository.findById(customerId).get();
        Cart cart = customer.getCart();

        List<Item> items = cart.getItems();
        for(Item itemEntity: items){
            if(itemEntity.getRequiredQuantity() == item.getRequiredQuantity()){
                cart.setNumberOfItems(cart.getItems().size() - 1);
            }
            else if(itemEntity.getRequiredQuantity() > item.getRequiredQuantity()){
                cart.setNumberOfItems(cart.getItems().size());
                itemEntity.setRequiredQuantity(itemEntity.getRequiredQuantity() - item.getRequiredQuantity());
            }
            else{
                throw new Exception("Enter correct quantity of " + itemEntity.getProduct().getName());
            }
        }

        int newTotal = cart.getCartTotal() - item.getRequiredQuantity() * item.getProduct().getPrice();
        if(newTotal >= 0){
            cart.setCartTotal(newTotal);
        }
        else{
            throw new Exception("No item is present in cart!!");
        }

        Cart savedCart = cartRepository.save(cart);

        CartResponseDto cartResponseDto = CartTransformer.CartToCartResponseDto(savedCart);

        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();
        for(Item itemEntity: savedCart.getItems()){
            itemResponseDtoList.add(ItemTransformer.ItemToItemResponseDto(itemEntity));
        }

        cartResponseDto.setItems(itemResponseDtoList);
        return cartResponseDto;

    }

    public List<ItemResponseDto> viewItems(int customerId) throws InvalidCustomerException {

        Customer customer;
        try{
            customer = customerRepository.findById(customerId).get();
        }
        catch (Exception e){
            throw new InvalidCustomerException("Customer Id is invalid !!");
        }

        List<Item> items = customer.getCart().getItems();

        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();
        for(Item item: items){
            itemResponseDtoList.add(ItemTransformer.ItemToItemResponseDto(item));
        }

        return itemResponseDtoList;
    }
}
