package com.ecom.application.Dto;

import com.ecom.application.entities.OrderItem;
import com.ecom.application.entities.OrderStatus;
import com.ecom.application.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;


    private User user;

    private BigDecimal totalAmount;


    private OrderStatus status;


    private List<OrderItemDto> items;

    private LocalDateTime createdAt;

}
