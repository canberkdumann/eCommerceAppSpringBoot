package com.example.eCommerceApp.controller;

import com.example.eCommerceApp.dto.RequestDto.ItemRequestDto;
import com.example.eCommerceApp.dto.ResponseDto.CartResponseDto;
import com.example.eCommerceApp.dto.ResponseDto.ItemResponseDto;
import com.example.eCommerceApp.exception.InvalidCustomerException;
import com.example.eCommerceApp.model.Item;
import com.example.eCommerceApp.service.CartService;
import com.example.eCommerceApp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ItemService itemService;

    @Autowired
    CartService cartService;


    @PostMapping("/add")
    public ResponseEntity addToCart(@RequestBody ItemRequestDto itemRequestDto) throws Exception {

        try{
            Item savedItem = itemService.addItem(itemRequestDto);
            CartResponseDto cartResponseDto = cartService.saveCart(itemRequestDto.getCustomerId(),savedItem);
            return new ResponseEntity(cartResponseDto,HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove_from_cart")
    public CartResponseDto removeFromCart(@RequestBody ItemRequestDto itemRequestDto) throws Exception {

        Item removeItem = itemService.removedItem(itemRequestDto);
        return cartService.removeFromCart(itemRequestDto.getCustomerId(), removeItem);
    }

    @GetMapping("view")
    public List<ItemResponseDto> viewItems(@RequestParam("customerId") int customerId) throws InvalidCustomerException {

        return cartService.viewItems(customerId);
    }

}
