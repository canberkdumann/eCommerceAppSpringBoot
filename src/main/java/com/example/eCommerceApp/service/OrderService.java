package com.example.eCommerceApp.service;

import com.example.eCommerceApp.dto.RequestDto.OrderRequestDto;
import com.example.eCommerceApp.dto.ResponseDto.ItemResponseDto;
import com.example.eCommerceApp.dto.ResponseDto.OrderResponseDto;
import com.example.eCommerceApp.exception.InvalidCustomerException;
import com.example.eCommerceApp.exception.InvalidProductException;
import com.example.eCommerceApp.model.*;
import com.example.eCommerceApp.repository.CustomerRepository;
import com.example.eCommerceApp.repository.OrderedRepository;
import com.example.eCommerceApp.repository.ProductRepository;
import com.example.eCommerceApp.transformer.ItemTransformer;
import com.example.eCommerceApp.transformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired ProductService productService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private OrderedRepository orderedRepository;

    public Ordered placeOrder(Customer customer) throws Exception {

        Cart cart = customer.getCart();

        Ordered order = new Ordered();
        order.setOrderNo(String.valueOf(UUID.randomUUID()));

        order.setCustomer(customer);

        List<Item> orderedItems = new ArrayList<>();
        for(Item item: cart.getItems()){
            try{
                productService.decreaseProductQuantity(item);
                orderedItems.add(item);
            } catch (Exception e) {
                throw new Exception("Product Out of stock");
            }
        }
        order.setItems(orderedItems);
        for(Item item: orderedItems)
            item.setOrder(order);
        order.setTotalValue(cart.getCartTotal());

        return order;
    }

    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws Exception {

        Customer customer;
        try{
            customer = customerRepository.findById(orderRequestDto.getCustomerId()).get();
        }
        catch (Exception e){
            throw new InvalidCustomerException("Customer Id is invalid !!");
        }

        Product product;
        try{
            product = productRepository.findById(orderRequestDto.getProductId()).get();
        }
        catch(Exception e){
            throw new InvalidProductException("Product doesn't exist");
        }

        Item item = Item.builder()
                .requiredQuantity(orderRequestDto.getRequiredQuantity())
                .product(product)
                .build();
        try{
            productService.decreaseProductQuantity(item);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

        Ordered order = new Ordered();
        order.setOrderNo(String.valueOf(UUID.randomUUID()));
        order.setCustomer(customer);
        order.setTotalValue(item.getRequiredQuantity()*product.getPrice());
        order.getItems().add(item);

        customer.getOrderList().add(order);
        item.setOrder(order);
        product.getItemList().add(item);

        Ordered savedOrder = orderedRepository.save(order); // order and item

        OrderResponseDto orderResponseDto = OrderTransformer.OrderToOrderResponseDto(savedOrder);

        List<ItemResponseDto> items = new ArrayList<>();
        for(Item itemEntity: savedOrder.getItems()){
            ItemResponseDto itemResponseDto = ItemTransformer.ItemToItemResponseDto(itemEntity);

            items.add(itemResponseDto);
        }

        orderResponseDto.setItems(items);

        return orderResponseDto;

    }

    public List<OrderResponseDto> getAllOrders(int customerId) throws InvalidCustomerException {

        Customer customer;
        try{
            customer = customerRepository.findById(customerId).get();
        }
        catch (Exception e){
            throw new InvalidCustomerException("Customer Id is invalid !!");
        }

        List<Ordered> orders = customer.getOrderList();

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for(Ordered order: orders){
            OrderResponseDto orderResponseDto = OrderTransformer.OrderToOrderResponseDto(order);

            List<Item> items = order.getItems();
            List<ItemResponseDto> itemResponseDtos = new ArrayList<>();
            for(Item item: items){
                ItemResponseDto itemResponseDto = ItemTransformer.ItemToItemResponseDto(item);
                itemResponseDtos.add(itemResponseDto);
            }

            orderResponseDto.setItems(itemResponseDtos);
            orderResponseDtoList.add(orderResponseDto);
        }

        return orderResponseDtoList;
    }

    public String deleteOrder(int customerId, String orderNo) throws InvalidCustomerException {

        Customer customer;
        try{
            customer = customerRepository.findById(customerId).get();
        }
        catch (Exception e){
            throw new InvalidCustomerException("Customer Id is invalid !!");
        }

        List<Ordered> orders = customer.getOrderList();
        for(Ordered order: orders){
            if(order.getOrderNo() == orderNo){
                orders.remove(order);
            }
        }

        customerRepository.save(customer);

        return "Order deleted successfully!";
    }

}
