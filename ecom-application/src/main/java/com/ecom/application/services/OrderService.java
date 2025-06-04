package com.ecom.application.services;

import com.ecom.application.Dto.OrderItemDto;
import com.ecom.application.Dto.OrderResponse;
import com.ecom.application.Repository.OrderRepository;
import com.ecom.application.Repository.UserRepository;
import com.ecom.application.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final CartService cartService;
    private final UserRepository userRepository;
    private  final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(String userId) {
        //Validate for cart items
        List<CartItem> cartItem = cartService.getCart(userId);
        if(cartItem.isEmpty())return Optional.empty();

        //Validate for user
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if(userOptional.isEmpty())return Optional.empty();
        User user = userOptional.get();

        //Calculate total price
        BigDecimal totalPrice = cartItem.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        //Create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);


    List<OrderItem> orderItems = cartItem.stream()
            .map(item -> new OrderItem(
                    null,
                    item.getProduct(),
                    item.getQuantity(),
                    item.getPrice(),
                    order
            )).collect(Collectors.toList());

            order.setItems(orderItems);
            Order savedOrder = orderRepository.save(order);

        //clear the cart
        cartService.clearOrder(userId);
        return Optional.of(mapToOrderResponse(savedOrder));
    }
    public OrderResponse mapToOrderResponse(Order order) {
        List<OrderItemDto> itemDtos = order.getItems()
                .stream()
                .map(this::mapToOrderItemDto)
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getUser(),
                order.getTotalAmount(),
                order.getStatus(),
                itemDtos,
                order.getCreatedAt()
        );
    }

    private OrderItemDto mapToOrderItemDto(OrderItem item) {
        return new OrderItemDto(
                item.getId(),
                item.getProduct().getId(),                    // productId
                item.getQuantity(),                           // quantity
                item.getPrice(),                              // price (per unit or item price)
                item.getPrice().multiply(                     // subTotal = price × quantity
                        BigDecimal.valueOf(item.getQuantity())
                )
        );
    }

}



