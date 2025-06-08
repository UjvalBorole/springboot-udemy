package com.ecommerce.order.Dto;

import com.ecommerce.order.entities.OrderStatus;
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


    private String userId;

    private BigDecimal totalAmount;


    private OrderStatus status;


    private List<OrderItemDto> items;

    private LocalDateTime createdAt;

}
