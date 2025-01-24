package com.deliveryBoy.service;

import java.util.List;
import java.util.UUID;

import com.deliveryBoy.entity.OrderEntity;
import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.enums.RejectOrderReason;
import com.deliveryBoy.request.OrderRequest;

public interface HomeService {

	List<OrderRequest> getTodayOrders(); // Changed to return OrderRequest

    List<OrderRequest> getOrdersByStatus(String status); // Changed to return OrderRequest

    void acceptOrder(String orderId);

    void rejectOrder(String orderId, RejectOrderReason reason);

    void toggleAvailability(UUID deliveryBoyId, AvailabilityStatus status); // Add this line

    int getNewOrdersCount();

    List<OrderEntity> getAllOrders(); // Changed to OrderEntity

    // Other methods like saving an order etc.
    OrderEntity saveOrder(OrderEntity order); // Changed to OrderEntity

}
