//package com.deliveryBoy.service;
//
//import java.util.List;
//
//import com.deliveryBoy.entity.OrderEntity;
//import com.deliveryBoy.enums.OrderStatus;
//import com.deliveryBoy.request.OrderRequest;
//import com.deliveryBoy.request.PaymentStatusRequest;
//import com.deliveryBoy.response.OrderResponse;
//
//
//public interface OrderService {
//
//	List<OrderResponse> getOrdersByCategory(String category);
//
//	void updateOrderStatus(Long orderId, String status);
//	
//	void verifyOtpAndMarkDelivered(Long orderId, String otp);
//	
//
//	OrderEntity getOrderById(Long orderId);
//
//	void saveOrder(OrderRequest orderRequest);
//
//	void updateOrderStatus(Long orderId, OrderStatus newStatus, String updatedBy);
//
//	void progressOrderStatus(Long orderId, OrderStatus currentStatus);
//
//	String generateOtpForDelivery(Long orderId, String customerId);
//
//	boolean cancelOrder(Long orderId, String reason);
//
//	void updatePaymentStatus(PaymentStatusRequest request);
//
//	
//
//}
