package com.deliveryBoy.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryBoy.entity.OrderEntity;
import com.deliveryBoy.entity.OrderStatusLog;
import com.deliveryBoy.entity.ProfileEntity;
import com.deliveryBoy.repository.OrderRepository;
import com.deliveryBoy.repository.OrderStatusLogRepository;
import com.deliveryBoy.response.OrderResponse;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;
	@Autowired
   private OrderStatusLogRepository orderStatusLogRepository;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	NotificationService notificationService;
	
	@Override
	public List<OrderResponse> getOrdersByCategory(String category) {
		List<OrderEntity> orders;
		
		LocalDate today = LocalDate.now();
		
		switch(category.toLowerCase()) {
		case "today":
			orders=orderRepository.findByOrderDateAndStatus(today,"Accepted");
			break;
		case "all":
			orders=orderRepository.findAll();
			break;
		case "cancelled":
			orders=orderRepository.findByStatusIn(Arrays.asList("rejected","cancelled"));
			break;
			
		default:
			throw new IllegalArgumentException("invalid category:"+ category);
	
		}
		
		return orders.stream()
				.map(OrderResponse::fromEntity)
				.collect(Collectors.toList());
		
		}//getorders by category


	@Override
	public void updateOrderStatus(Long orderId, String newStatus) {
	
		 updateOrderStatus(orderId, newStatus, "System");	 
       
	}//
	
	public String generateOtp() {
	    Random random = new Random();
	    int otp = 100000 + random.nextInt(900000); 
	    return String.valueOf(otp);
	}//generate otp


	@Override
	public void verifyOtpAndMarkDelivered(Long orderId, String otp) {
		
		OrderEntity order = orderRepository.findById(orderId)
        .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + orderId));
		
		 if (!"In Transit".equals(order.getStatus())) {
	            throw new IllegalArgumentException("Order is not eligible for delivery. Current status: " + order.getStatus());
	        }
		 if (!otp.equals(order.getOtp())) {
	            throw new IllegalArgumentException("Invalid OTP.");
	        }
		 
		 order.setStatus("Successfully Delivered");
	        orderRepository.save(order); 
	
	}


	@Override
	public void updateOrderStatus(Long orderId, String newStatus, String updatedBy) {
		
		OrderEntity order = orderRepository.findById(orderId)
	            .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + orderId));

	    if (!"Accepted".equals(order.getStatus()) && !"Picked Up".equals(order.getStatus())) {
	        throw new IllegalArgumentException("Order status cannot be updated from: " + order.getStatus());
	    }

	    if (!"Picked Up".equals(newStatus) && !"In Transit".equals(newStatus)) {
	        throw new IllegalArgumentException("Invalid status: " + newStatus);
	    }

	//today orders should be pickedup or in transit	
		
		
		
	    if ("In Transit".equals(newStatus)) {
	        String otp = generateOtp();
	        order.setOtp(otp);
	    }

	    logStatusUpdate(order, order.getStatus(), newStatus, updatedBy);

	    order.setStatus(newStatus);
	    orderRepository.save(order);
	    
	    
	    
	    ProfileEntity deliveryBoy = order.getAssignedDeliveryBoy(); 
	    
	    if(deliveryBoy!=null) {
	    	String description = String.format("Order #%d  for customer %s status changed from %s to %s.",
	                orderId,order.getCustomerName(),order.getStatus(), newStatus);
	        notificationService.createNotification(deliveryBoy, order, description);
	    }
	    
		
	}

	private void logStatusUpdate(OrderEntity order, String previousStatus, String newStatus, String updatedBy) {
	    OrderStatusLog log = new OrderStatusLog();
	    log.setOrder(order);
	    log.setPreviousStatus(previousStatus);
	    log.setNewStatus(newStatus);
	    log.setUpdatedAt(LocalDateTime.now());
	    log.setUpdatedBy(updatedBy);

	    orderStatusLogRepository.save(log); 
	}


	@Override
	public OrderEntity getOrderById(Long orderId) {
	
		return orderRepository.findById(orderId).orElse(null);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
