package com.deliveryBoy.response;

import java.time.LocalDate;

import com.deliveryBoy.entity.OrderEntity;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class OrderResponse {
	private Long orderId;
    private String storeName;
    private String deliveryAddress;
    private LocalDate orderDate;
    private String status;
    
    //payment mode
    
    public static OrderResponse fromEntity(OrderEntity order) {
        OrderResponse orderResponse = OrderResponse.builder()
        .orderId(order.getId())
        .storeName(order.getStoreName())
        .deliveryAddress(order.getDeliveryAddress())
        .orderDate(order.getOrderDate())
        .status(order.getStatus())
        .build();
        
        return orderResponse;
        
        
        
        
        
    }
}
