package com.deliveryBoy.service;

import java.util.List;
import java.util.UUID;

import com.deliveryBoy.entity.OrderEntity;
import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.enums.OrderStatus;

import com.deliveryBoy.request.OrderRequest;
import com.deliveryBoy.response.OrderResponse;

public interface OrderService {

	OrderEntity createOrder(OrderRequest orderRequest);

	List<OrderResponse> getAllOrders();

	List<OrderResponse> canceledOrders();

	List<OrderResponse> getTodayOrders();

	List<OrderRequest> getOrdersByStatus(String status);

	int getNewOrdersCount();

	OrderEntity saveOrder(OrderEntity order);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	List<OrderRequest> getTodayOrders(String status); 
//	List<OrderRequest> getCanceledOrders(String status); 
//
//    List<OrderRequest> getOrdersByStatus(String status);
//
//    void acceptOrder(String orderId);
//
//   // void rejectOrder(String orderId, RejectOrderReason reason);
//
//    void toggleAvailability(UUID deliveryBoyId, AvailabilityStatus status);
//
//    int getNewOrdersCount();
//
//    List<OrderResponse> getAllOrders();
//
//
//    OrderEntity saveOrder(OrderEntity order);
//
//	OrderEntity createOrder(OrderRequest orderRequest);
//
//	List<OrderResponse> canceledOrders();
//
//	void pickupOrder(String orderId);
	
	
	

////    List<OrderResponse> getTodayOrders();
//
//    //List<OrderRequest> getOrdersByStatus(String status);
//
//    void acceptOrder(String orderId);
//    
//    List<OrderRequest> getCanceledOrders(String status); 
//    List<OrderRequest> getOrdersByStatus(String status);
//    
//
////    void rejectOrder(String orderId, RejectOrderReason reason);
//
////    void toggleAvailability(UUID deliveryBoyId, AvailabilityStatus status);
//
//    int getNewOrdersCount();
//
// List<OrderResponse> getAllOrders();
//
//
//    OrderEntity saveOrder(OrderEntity order);
//
//	OrderEntity createOrder(OrderRequest orderRequest);
//
////	List<OrderResponse> canceledOrders();
//
//	List<OrderRequest> getTodayOrders(String status);
//
////	void pickupOrder(String orderId);
    
}
