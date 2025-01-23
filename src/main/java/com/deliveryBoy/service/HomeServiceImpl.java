package com.deliveryBoy.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryBoy.entity.OrderEntity;
import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.enums.OrderStatus;
import com.deliveryBoy.enums.RejectOrderReason;
import com.deliveryBoy.repository.OrderRepository;
import com.deliveryBoy.request.OrderRequest;


@Service
public class HomeServiceImpl  implements HomeService{

	 @Autowired
	    private OrderRepository orderRepository;

	    @Override
	    public List<OrderRequest> getTodayOrders() {

	        LocalDate today = LocalDate.now();
	        List<OrderEntity> orders = orderRepository.findByOrderDate(today);

	        if (orders.isEmpty()) {
	            throw new RuntimeException("No orders found today");
	        }

	        return orders.stream()
	                .map(order -> OrderRequest.builder()
	                        .orderId(order.getId().toString())
	                        .customerName(order.getCustomerName())
	                        .deliveryAddress(order.getDeliveryAddress())
	                        .contactNumber(order.getContactNumber())
	                        .orderstatus(order.getOrderstatus().toString())
	                        .build())
	                .collect(Collectors.toList());
	    }

	    @Override
	    public List<OrderRequest> getOrdersByStatus(String status) {
	        OrderStatus orderStatus;
	        try {
	            orderStatus = OrderStatus.valueOf(status.toUpperCase());
	        } catch (IllegalArgumentException e) {
	            throw new RuntimeException("Invalid status value: " + status);
	        }

	        List<OrderEntity> orders = orderRepository.findOrdersByStatus(orderStatus);

	        // Convert List<OrderEntity> to List<OrderRequest>
	        return orders.stream()
	                .map(order -> OrderRequest.builder()
	                        .orderId(order.getId().toString())
	                        .customerName(order.getCustomerName())
	                        .deliveryAddress(order.getDeliveryAddress())
	                        .contactNumber(order.getContactNumber())
	                        .orderstatus(order.getOrderstatus().toString())
	                        .build())
	                .collect(Collectors.toList());
	    }

	    @Override
	    public void acceptOrder(String orderId) {
	        OrderEntity orderEntity = orderRepository.findById(UUID.fromString(orderId))
	                .orElseThrow(() -> new RuntimeException("Order not found"));
	        orderEntity.setOrderstatus(OrderStatus.ACCEPTED);
	        orderRepository.save(orderEntity);
	    }

	    @Override
	    public void rejectOrder(String orderId, RejectOrderReason reason) {
	        OrderEntity orderEntity = orderRepository.findById(UUID.fromString(orderId))
	                .orElseThrow(() -> new RuntimeException("Order not found"));
	        orderEntity.setOrderstatus(OrderStatus.REJECTED);
	        orderEntity.setRejection(reason);
	        orderRepository.save(orderEntity);
	    }

	    @Override
	    public void toggleAvailability(UUID deliveryBoyId, AvailabilityStatus status) {
	        OrderEntity deliveryBoy = orderRepository.findById(deliveryBoyId)
	                .orElseThrow(() -> new RuntimeException("Delivery boy not found"));

	        if (deliveryBoy.getAvailabilityStatus() == null) {
	            deliveryBoy.setAvailabilityStatus(AvailabilityStatus.OFFLINE);
	        } else {
	            deliveryBoy.setAvailabilityStatus(status);
	        }

	        orderRepository.save(deliveryBoy);
	    }

	    @Override
	    public int getNewOrdersCount() {
	        return orderRepository.countByStatus(OrderStatus.NEW);
	    }

	    @Override
	    public List<OrderEntity> getAllOrders() {
	        return orderRepository.findAll();
	    }

	    @Override
	    public OrderEntity saveOrder(OrderEntity order) {
	        return orderRepository.save(order);
	    }
}
