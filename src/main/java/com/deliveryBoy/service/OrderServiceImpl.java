//package com.deliveryBoy.service;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import javax.persistence.EntityNotFoundException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.deliveryBoy.entity.Address;
//import com.deliveryBoy.entity.OTPdetails;
//import com.deliveryBoy.entity.OrderEntity;
//import com.deliveryBoy.entity.OrderStatusLog;
//import com.deliveryBoy.entity.ProfileEntity;
//import com.deliveryBoy.enums.OrderStatus;
//import com.deliveryBoy.enums.PaymentStatus;
//import com.deliveryBoy.repository.OrderRepository;
//import com.deliveryBoy.repository.OrderStatusLogRepository;
//import com.deliveryBoy.request.OrderRequest;
//import com.deliveryBoy.request.PaymentStatusRequest;
//import com.deliveryBoy.response.OrderResponse;
//
//@Service
//public class OrderServiceImpl implements OrderService {
//
//	@Autowired
//	private OrderRepository orderRepository;
//	@Autowired
//	private OrderStatusLogRepository orderStatusLogRepository;
//
//	@Autowired
//	private ProfileService profileService;
//
//	@Autowired
//	NotificationService notificationService;
//	@Autowired
//	OtpService otpService;
//
//	@Override
//	public List<OrderResponse> getOrdersByCategory(String category) {
//		List<OrderEntity> orders;
//
//		LocalDate today = LocalDate.now();
//
//		switch (category.toLowerCase()) {
//		case "today":
//			orders = orderRepository.findByOrderDateAndStatus(today, OrderStatus.ACCEPTED);
//			break;
//		case "all":
//			orders = orderRepository.findAll();
//			break;
//		case "cancelled":
//			orders = orderRepository.findByStatusIn(Arrays.asList(OrderStatus.REJECTED, OrderStatus.CANCELLED));
//			break;
//
//		default:
//			throw new IllegalArgumentException("invalid category:" + category);
//
//		}
//
//		return orders.stream().map(OrderResponse::fromEntity).collect(Collectors.toList());
//
//	}// getorders by category
//
//	@Override
//	public void updateOrderStatus(Long orderId, String newStatusStr) {
//
//		OrderStatus newStatus = OrderStatus.valueOf(newStatusStr.toUpperCase());
//
//		updateOrderStatus(orderId, newStatus, "System");
//
//	}//
//
//	
//	
//	@Override
//	public void verifyOtpAndMarkDelivered(Long orderId, String otp) {
//	    // Check OTP in the cache
//	    OTPdetails otpDetails = otpCache.get(orderId.toString());
//
//	    if (otpDetails != null 
//	            && otpDetails.getOtp().equals(otp) 
//	            && LocalDateTime.now().isBefore(otpDetails.getExpirationTime())) {
//
//	        // Mark the order as delivered
//	        OrderEntity order = orderRepository.findById(orderId)
//	                .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + orderId));
//	        if (order.getStatus() != OrderStatus.IN_TRANSIT) {
//	            throw new IllegalArgumentException("Order is not eligible for delivery. Current status: " + order.getStatus());
//	        }
//
//	        order.setStatus(OrderStatus.DELIVERED);
//	        orderRepository.save(order);
//
//	        // Log delivery event
//	        logStatusUpdate(order, "IN_TRANSIT", "DELIVERED", "System");
//
//	        // Invalidate the OTP
//	        otpCache.remove(orderId.toString());
//	    } else {
//	        throw new IllegalArgumentException("Invalid or expired OTP.");
//	    }
//	}
//
//
//	@Override
//	public void updateOrderStatus(Long orderId, OrderStatus newStatus, String updatedBy) {
//
//		OrderEntity order = orderRepository.findById(orderId)
//				.orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + orderId));
//
//		if (order.getStatus() != OrderStatus.ACCEPTED && order.getStatus() != OrderStatus.PICKED_UP) {
//			throw new IllegalArgumentException("Order status cannot be updated from: " + order.getStatus());
//		}
//
//		if (newStatus != OrderStatus.PICKED_UP && newStatus != OrderStatus.IN_TRANSIT) {
//			throw new IllegalArgumentException("Invalid status: " + newStatus);
//		}
//
//		if (newStatus == OrderStatus.IN_TRANSIT) {
//			String otp = otpService.generateOtp();
//			order.setOtp(otp);
//		}
//
//		logStatusUpdate(order, order.getStatus().name(), newStatus.name(), updatedBy);
//
//		order.setStatus(newStatus);
//		orderRepository.save(order);
//
//		ProfileEntity deliveryBoy = order.getAssignedDeliveryBoy();
//		if (deliveryBoy != null) {
//			String description = String.format("Order #%d for customer %s status changed from %s to %s.", orderId,
//					order.getCustomerName(), order.getStatus(), newStatus);
//			notificationService.createNotification(deliveryBoy, order, description);
//		}
//
//	}
//
//	private void logStatusUpdate(OrderEntity order, String previousStatus, String newStatus, String updatedBy) {
//		OrderStatusLog log = new OrderStatusLog();
//		log.setOrder(order);
//		log.setPreviousStatus(previousStatus);
//		log.setNewStatus(newStatus);
//		log.setUpdatedAt(LocalDateTime.now());
//		log.setUpdatedBy(updatedBy);
//
//		orderStatusLogRepository.save(log);
//	}
//
//	@Override
//	public OrderEntity getOrderById(Long orderId) {
//
//		return orderRepository.findById(orderId) .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + orderId));
//	}
//
//	@Override
//	public void saveOrder(OrderRequest orderRequest) {
//
//		Address address = Address.builder().street(orderRequest.getStreet()).city(orderRequest.getCity())
//				.state(orderRequest.getState()).zipCode(orderRequest.getZipCode()).country(orderRequest.getCountry())
//				.build();
//
//		
//		OrderEntity order = OrderEntity.builder().storeName(orderRequest.getStoreName()).deliveryAddress(address)
//				.orderDate(orderRequest.getOrderDate())
//				.status(OrderStatus.valueOf(orderRequest.getStatus().toUpperCase()))
//				.paymentMode(orderRequest.getPaymentMode())
//				.build();
//
//		orderRepository.save(order);
//		
//		
//
//	}
//
//	public Address getAddressByOrderId(Long orderId) {
//		OrderEntity order = orderRepository.findById(orderId)
//				.orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + orderId));
//		return order.getDeliveryAddress();
//	}
//
//	// real time address is pending
//
//	@Override
//	public void progressOrderStatus(Long orderId, OrderStatus currentStatus) {
//		Optional<OrderEntity> optionalOrder = orderRepository.findById(orderId);
//
//		if (optionalOrder.isPresent()) {
//			OrderEntity order = optionalOrder.get();
//
//			switch (currentStatus) {
//			case PICKED_UP:
//				order.setStatus(OrderStatus.OUT_FOR_DELIVERY);
//				break;
//			case OUT_FOR_DELIVERY:
//				order.setStatus(OrderStatus.DELIVERED);
//				break;
//			// Add more transitions as needed
//			default:
//				throw new IllegalArgumentException("Invalid status progression.");
//			}
//
//			orderRepository.save(order);
//		} else {
//			throw new EntityNotFoundException("Order not found with ID: " + orderId);
//		}
//	}
//
//	 private static final int OTP_EXPIRATION_TIME = 5; // minutes
//	    private Map<String, OTPdetails> otpCache = new HashMap<>(); 
//	
//	@Override
//	public String generateOtpForDelivery(Long orderId, String customerId) {
//		String otp = otpService.generateOtp();
//        sendOtpToCustomer(customerId, otp);
//        
//        OTPdetails otpDetails = new OTPdetails(otp, LocalDateTime.now().plusMinutes(OTP_EXPIRATION_TIME));
//        otpCache.put(orderId.toString(), otpDetails);
//
//        // Log OTP generation event
//        orderStatusLogRepository.saveStatus(orderId.toString(), "OTP Generated", LocalDateTime.now());
//        
//        return otp;
//	}
//
//	private void sendOtpToCustomer(String customerId, String otp) {
//		try {Long userId = Long.parseLong(customerId);
//		notificationService.sendNotification(userId,"otp is"+otp);
//		}catch (NumberFormatException e) {
//	        throw new IllegalArgumentException("Invalid customerId: " + customerId, e);
//	    }
//		
//	}
//
//	
//
//	
//	//real time status changes
//	
//	@Override
//	public boolean cancelOrder(Long orderId, String reason) {
//		System.out.println("Order ID: " + orderId + " cancelled for reason: " + reason);
//
//        // Update the status to CANCELLED
//        OrderStatus orderStatus = OrderStatus.CANCELLED;
//
//        
//        System.out.println("Order status is: " + orderStatus);
//
//        return true; 
//    }//
//
//	@Override
//	public void updatePaymentStatus(PaymentStatusRequest request) {
//		 OrderEntity order = orderRepository.findById(request.getOrderId())
//		            .orElseThrow(() -> new EntityNotFoundException("Order not found for ID: " + request.getOrderId()));
//		        
//		 if (order.getPaymentStatus() == PaymentStatus.RECEIVED) {
//	            throw new IllegalArgumentException("Payment is already marked as received.");
//	        }
//		 
//		 order.setPaymentStatus(PaymentStatus.RECEIVED);
//	        orderRepository.save(order); 
//		 
//	        String notificationMessage = String.format("Payment for Order #%d has been received.", order.getId());
//	        notificationService.sendNotification(order.getAssignedDeliveryBoy().getUserid(), notificationMessage);
//		 
//		 
//	}
//			
//	public void generateAndSendOtp(Long orderId, String Contact) {
//        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
//
//        String otp = otpService.generateOtp();
//        order.setOtp(otp);
//        order.setOtpGeneratedTime(LocalDateTime.now());
//        orderRepository.save(order);
//
//        // Send OTP to the customer (e.g., via SMS or email)
//        System.out.println("OTP sent to customer: " + otp);
//    }
//
//    public boolean verifyOtp(Long orderId, String providedOtp) {
//        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
//
//        return otpService.isOtpValid(order.getOtp(), providedOtp, order.getOtpGeneratedTime());
//    }
//
//    
//}
