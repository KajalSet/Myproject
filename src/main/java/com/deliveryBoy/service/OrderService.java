package com.deliveryBoy.service;

import java.util.List;

import com.deliveryBoy.response.OrderResponse;

public interface OrderService {

	List<OrderResponse> getOrdersByCategory(String category);

	void updateOrderStatus(Long orderId, String status);
	void verifyOtpAndMarkDelivered(Long orderId, String otp);
	void updateOrderStatus(Long orderId, String newStatus, String updatedBy); 

}
