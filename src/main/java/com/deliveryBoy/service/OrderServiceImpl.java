package com.deliveryBoy.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryBoy.entity.OrderEntity;
import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.enums.OrderStatus;

import com.deliveryBoy.exception.OrderNotFoundException;
import com.deliveryBoy.repository.OrderRepository;
import com.deliveryBoy.request.OrderRequest;
import com.deliveryBoy.response.OrderResponse;

@Service
public class OrderServiceImpl implements OrderService {
	
    @Autowired
    private OrderRepository orderRepository;
    
    @Override
    public OrderEntity createOrder(OrderRequest orderRequest) {
       
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomerName(orderRequest.getCustomerName());
        orderEntity.setDeliveryAddress(orderRequest.getDeliveryAddress());
        orderEntity.setContactNumber(orderRequest.getContactNumber());
       
        orderEntity.setOrderDate(LocalDate.now()); 
        orderEntity.setOrderstatus(OrderStatus.PENDING);
        orderEntity.setAvailabilityStatus(AvailabilityStatus.valueOf(orderRequest.getAvailabilityStatus())); 
        orderEntity.setCreatedAt(LocalDateTime.now());
        try {
            UUID deliveryBoyId = UUID.fromString(orderRequest.getDeliveryBoyId());
            orderEntity.setDeliveryBoyId(deliveryBoyId); 
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid UUID format for deliveryBoyId: " + orderRequest.getDeliveryBoyId());
        }
        
        
        
        orderEntity = orderRepository.save(orderEntity);
        System.out.println("Saved OrderEntity: " + orderEntity);
       
        return orderEntity;
    }
    
    
    
    @Override
    public List<OrderResponse> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();

        return orders.stream()
                .map(order -> OrderResponse.builder()
                        .orderId(order.getId().toString())
                        .date(order.getOrderDate().toString())
                        .store(order.getCustomerName())
                        .deliveryLocation(order.getDeliveryAddress())
                        .orderStatus(order.getOrderstatus()) // Include orderStatus
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> canceledOrders() {
        List<OrderEntity> orders = orderRepository.findByOrderstatusIn(
                List.of(OrderStatus.REJECTED, OrderStatus.CANCELLED));

        return orders.stream()
                .map(order -> OrderResponse.builder()
                        .orderId(order.getId().toString())
                        .date(order.getOrderDate().toString())
                        .store(order.getCustomerName())
                        .deliveryLocation(order.getDeliveryAddress())
                        .orderStatus(order.getOrderstatus()) // Include orderStatus
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getTodayOrders() {
        LocalDate today = LocalDate.now();
        List<OrderEntity> orders = orderRepository.findByOrderDate(today)
                .stream()
                .filter(order -> order.getOrderstatus() == OrderStatus.ACCEPTED) // Filter only accepted orders
                .collect(Collectors.toList());

        return orders.stream()
                .map(order -> OrderResponse.builder()
                        .orderId(order.getId().toString())
                        .date(order.getOrderDate().toString())
                        .store(order.getCustomerName())
                        .deliveryLocation(order.getDeliveryAddress())
                        .orderStatus(order.getOrderstatus()) // Include orderStatus
                        .build())
                .collect(Collectors.toList());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 
    public List<OrderRequest> convertToOrderRequests(List<OrderEntity> orders) {
        return orders.stream()
                .map(order -> OrderRequest.builder()
                        .orderId(order.getId().toString())
                        .customerName(order.getCustomerName())
                        .deliveryAddress(order.getDeliveryAddress())
                        .contactNumber(order.getContactNumber())
                        .orderstatus(order.getOrderstatus().toString()) // Convert OrderStatus to String
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
        return convertToOrderRequests(orders);
    }

    public void acceptOrder(String orderId) {
        OrderEntity orderEntity = orderRepository.findById(UUID.fromString(orderId)).orElseThrow(() -> new OrderNotFoundException(orderId));
        orderEntity.setOrderstatus(OrderStatus.ACCEPTED);
        orderEntity.setUpdatedAt(LocalDateTime.now());  // Update timestamp on accept
        orderRepository.save(orderEntity);
    }

    

    @Override
    public int getNewOrdersCount() {
        return orderRepository.countByStatus(OrderStatus.NEW);
    }



    @Override
    public OrderEntity saveOrder(OrderEntity order) {
        return orderRepository.save(order);
    }
    
    
   
    public List<OrderRequest> orderRequests(List<OrderEntity> orders) {
        return orders.stream()
                .map(order -> OrderRequest.builder()
                        .orderId(order.getId().toString())
                        .customerName(order.getCustomerName())
                        .deliveryAddress(order.getDeliveryAddress())
                        .contactNumber(order.getContactNumber())
                        .orderstatus(order.getOrderstatus().toString()) // Convert OrderStatus to String
                        .build())
                .collect(Collectors.toList());
    }
    
    
//    
//    @Override
//    public void pickupOrder(String orderId) {
//        OrderEntity orderEntity = orderRepository.findById(UUID.fromString(orderId))
//                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
//
//        if (orderEntity.getOrderstatus() == OrderStatus.PICKED_UP) {
//            throw new RuntimeException("Order is already picked up");
//        }
//
//        orderEntity.setOrderstatus(OrderStatus.PICKED_UP);
//        orderEntity.setUpdatedAt(LocalDateTime.now());
//
//        orderRepository.save(orderEntity);
//    }
//    

//    public void rejectOrder(String orderId, String reason) {
//        // Find the order by ID
//        OrderEntity orderEntity = orderRepository.findById(UUID.fromString(orderId))
//                .orElseThrow(() -> new OrderNotFoundException(orderId));
//        
//        // Set order status to REJECTED and update timestamp
//        orderEntity.setOrderstatus(OrderStatus.REJECTED);
//        orderEntity.setUpdatedAt(LocalDateTime.now());
//        
//        // Set the rejection reason (which is of type RejectOrderReason)
//        orderEntity.setRejection(reason);  // No need to call `name()`
//        
//        // Save the updated order entity
//        orderRepository.save(orderEntity);
//    }
 
    
//    public List<OrderRequest> getRejectedOrCanceledOrders() {
//        // Fetch orders where the status is either REJECTED or CANCELED
//        List<OrderEntity> orders = orderRepository.findByOrderstatusIn(
//                List.of(OrderStatus.REJECTED, OrderStatus.CANCELED));
//        
//        return convertToOrderRequests(orders);  // Convert OrderEntity to OrderRequest
//    }
    
    
//    @Override
//    public void toggleAvailability(UUID deliveryBoyId, AvailabilityStatus status) {
//        OrderEntity deliveryBoy = orderRepository.findById(deliveryBoyId)
//                .orElseThrow(() -> new RuntimeException("Delivery boy not found"));
//
//        if (deliveryBoy.getAvailabilityStatus() == null) {
//            deliveryBoy.setAvailabilityStatus(AvailabilityStatus.OFFLINE);
//        } else {
//            deliveryBoy.setAvailabilityStatus(status);
//        }
//
//        orderRepository.save(deliveryBoy);
//    }
//    @Override
//	 public List<OrderRequest> getTodayOrders(String status) {
//	     // Convert the provided status to the OrderStatus enum
//	     OrderStatus orderStatus;
//	     try {
//	         orderStatus = OrderStatus.valueOf(status.toUpperCase());
//	     } catch (IllegalArgumentException e) {
//	         throw new RuntimeException("Invalid order status: " + status);
//	     }
//
//	     // Fetch orders by status
//	     List<OrderEntity> orders = orderRepository.findOrdersByStatus(orderStatus);
//
//	     if (orders.isEmpty()) {
//	         throw new RuntimeException("No orders found with status: " + status);
//	     }
//
//	     // Map the orders to OrderRequest
//	     return orders.stream()
//	             .map(order -> OrderRequest.builder()
//	                     .orderId(order.getId().toString())
//	                     .customerName(order.getCustomerName())
//	                     .deliveryAddress(order.getDeliveryAddress())
//	                     .contactNumber(order.getContactNumber())
//	                     .orderstatus(order.getOrderstatus().toString())
//	                     .build())
//	             .collect(Collectors.toList());
//	 }

}
