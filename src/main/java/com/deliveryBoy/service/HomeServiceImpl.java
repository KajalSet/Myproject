//package com.deliveryBoy.service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.deliveryBoy.entity.HomeEntity;
//import com.deliveryBoy.enums.AvailabilityStatus;
//import com.deliveryBoy.enums.OrderStatus;
//import com.deliveryBoy.enums.RejectOrderReason;
//import com.deliveryBoy.repository.HomeRepository;
//import com.deliveryBoy.request.HomeRequest;
//
//@Service
//public class HomeServiceImpl  implements HomeService{
//
//	@Autowired
//	private HomeRepository homeRepository;
//
//	@Override
//	public List<HomeRequest> getTodayOrders() {
//		
//		LocalDate today = LocalDate.now();
//		List<HomeEntity> orders = homeRepository.findOrdersByDateAndStatus(today);
//		
//		if(orders.isEmpty()) {
//			throw new RuntimeException("No orders found today");
//		}
//		
//		return orders.stream()
//				.map(order ->HomeRequest.builder()
//						.orderId(order.getOrderId())
//						.customerName(order.getCustomerName())
//						.deliveryAddress(order.getDeliveryAddress())
//						.contactNumber(order.getContactNumber())
//						.status(order.getStatus())
//						.build())
//						.collect(Collectors.toList());
//				
//	}//get today orders
//
//	@Override
//	public List<HomeRequest> getOrdersByStatus(String status) {
//		
//		List<HomeEntity> orders = homeRepository.findOrderByStatus(status);
//		
//		return orders.stream()
//				.map(order ->HomeRequest.builder()
//						.orderId(order.getOrderId())
//						.customerName(order.getCustomerName())
//						.deliveryAddress(order.getDeliveryAddress())
//						.contactNumber(order.getContactNumber())
//						.status(order.getStatus())
//						.build())
//						.collect(Collectors.toList());
//		
//	}
//
//	@Override
//	public void acceptOrder(String orderId) {
//		HomeEntity order=homeRepository.findByOrderId(orderId).orElseThrow(()->new RuntimeException("Order not found"));
//		
//		order.setOrderstatus(OrderStatus.ACCEPTED);
//		
//		//order.setSection("Order");
//		homeRepository.save(order);
//	}
//
//	@Override
//	public void rejectOrder(String orderId, RejectOrderReason reason) {
//		HomeEntity order = homeRepository.findByOrderId(orderId)
//	            .orElseThrow(() -> new RuntimeException("Order not found"));
//	        order.setOrderstatus(OrderStatus.REJECTED);
//	        order.setRejection(reason);
//	        //order.setSection("Home");
//	        
//	        homeRepository.save(order);
//	}
//
//	@Override
//	public void toggleAvailabilty(Long deliveryBoyId, AvailabilityStatus status) {
//		
//		HomeEntity deliveryBoy = homeRepository.findById(deliveryBoyId)
//	            .orElseThrow(() -> new RuntimeException("Delivery boy not found"));
//
//	    
//	        deliveryBoy.setAvailabilityStatus(status);
//	        homeRepository.save(deliveryBoy);
//		
//		
//	}
//
//	@Override
//	public int getNewOrdersCount() {
//		
//		return homeRepository.countByStatus(OrderStatus.NEW);
//		
//		//apache kafka
//	}
//	
//	
//	
//	
//	
//}
