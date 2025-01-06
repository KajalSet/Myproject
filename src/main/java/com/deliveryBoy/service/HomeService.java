//package com.deliveryBoy.service;
//
//import java.util.List;
//
//import com.deliveryBoy.enums.AvailabilityStatus;
//import com.deliveryBoy.enums.RejectOrderReason;
//import com.deliveryBoy.request.HomeRequest;
//
//public interface HomeService {
//
//	List<HomeRequest> getTodayOrders();
//
//	List<HomeRequest> getOrdersByStatus(String status);
//
//	void acceptOrder(String orderId);
//
//	void rejectOrder(String orderId, RejectOrderReason reason);
//
//	void toggleAvailabilty(Long delivaeryBoyId, AvailabilityStatus status);
//
//	int getNewOrdersCount();
//
//}
