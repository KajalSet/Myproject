package com.deliveryBoy.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryBoy.auth.User;
import com.deliveryBoy.entity.OrderEntity;
import com.deliveryBoy.entity.OtpResponse;
import com.deliveryBoy.entity.SendOtp;
import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.enums.OrderStatus;

import com.deliveryBoy.repository.OrderRepository;
import com.deliveryBoy.repository.UserRepository;
import com.deliveryBoy.request.OrderRequest;


@Service
public class HomeServiceImpl  implements HomeService{

	 @Autowired
	    private OrderRepository orderRepository;
	 
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
    private WebClientService webClientService;
	 
	 
	 @Override
		public List<OrderRequest> getTodayOrders() {

			LocalDate today = LocalDate.now();
			List<OrderEntity> orders = orderRepository.findByOrderDate(today).stream()
					.filter(order -> order.getOrderstatus() == OrderStatus.PENDING) 
					.collect(Collectors.toList());

			if (orders.isEmpty()) {
				throw new RuntimeException("No orders found today");
			}

			return orders.stream()
					.map(order -> OrderRequest.builder().orderId(order.getId().toString())
							.customerName(order.getCustomerName()).deliveryAddress(order.getDeliveryAddress())
							.contactNumber(order.getContactNumber()).orderstatus(order.getOrderstatus().toString()).build())
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

//	    @Override
//	    public void acceptOrder(String orderId) {
//	        OrderEntity orderEntity = orderRepository.findById(UUID.fromString(orderId))
//	                .orElseThrow(() -> new RuntimeException("Order not found"));
//	        orderEntity.setOrderstatus(OrderStatus.ACCEPTED);
//	        orderRepository.save(orderEntity);
//	    }

	    @Override
	    public void acceptOrder(String orderId) {
	        OrderEntity orderEntity = orderRepository.findById(orderId)
	                .orElseThrow(() -> new RuntimeException("Order not found"));
	        orderEntity.setOrderstatus(OrderStatus.ACCEPTED);
	        orderRepository.save(orderEntity);
	    }
	    @Override
	    public void rejectOrder(String orderId, String reason) {
	        OrderEntity orderEntity = orderRepository.findById(UUID.fromString(orderId))
	                .orElseThrow(() -> new RuntimeException("Order not found"));
	        orderEntity.setOrderstatus(OrderStatus.REJECTED);
	        orderEntity.setRejection(reason);  // Now setting as a String
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
	    
	    
//	    @Override
//	    public OtpResponse sendOtpToDeliveryBoy(String orderId) throws Exception{
//	        // Fetch the order details
//	        OrderEntity orderEntity = orderRepository.findById(UUID.fromString(orderId))
//	                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//	        // Retrieve the delivery boy ID
//	        UUID deliveryBoyId = orderEntity.getDeliveryBoyId();
//	        if (deliveryBoyId == null) {
//	            throw new RuntimeException("Delivery boy is not assigned to this order");
//	        }
//
//	        // Fetch delivery boy details from the User table
//	        User deliveryBoy = userRepo.findById(deliveryBoyId)
//	                .orElseThrow(() -> new RuntimeException("Delivery boy details not found"));
//
//	        // Send OTP using the existing method
//	        SendOtp sendOtpRequest = new SendOtp();
//	        sendOtpRequest.setMobile(deliveryBoy.getMobileNumber());
//
//	        return sendOtp(sendOtpRequest); // Reusing your existing sendOtp method
//	    }
//

	    //working and respose is 200
	    @Override
	    public OtpResponse sendOtpToDeliveryBoy(String orderId) throws Exception {
	        // Fetch the order details using String ID
	        OrderEntity orderEntity = orderRepository.findById(orderId)
	                .orElseThrow(() -> new RuntimeException("Order not found"));

	        // Retrieve the delivery boy ID
	        UUID deliveryBoyId = orderEntity.getDeliveryBoyId();
	        if (deliveryBoyId == null) {
	            throw new RuntimeException("Delivery boy is not assigned to this order");
	        }

	        // Fetch delivery boy details from the User table
	        User deliveryBoy = userRepo.findById(deliveryBoyId)
	                .orElseThrow(() -> new RuntimeException("Delivery boy details not found"));

	        // Send OTP using the existing method
	        SendOtp sendOtpRequest = new SendOtp();
	        sendOtpRequest.setMobile(deliveryBoy.getMobileNumber());

	        return sendOtp(sendOtpRequest); // Reusing your existing sendOtp method
	    }

		private OtpResponse sendOtp(SendOtp sendOtpRequest) {
			// TODO Auto-generated method stub
			return null;
		}

//otp generation a random number-working a random number is created
	
//	    @Override
//	    public OtpResponse sendOtpToDeliveryBoy(String orderId) throws Exception {
//	        // Fetch the order details
//	        OrderEntity orderEntity = orderRepository.findById(orderId)
//	                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//	        // Retrieve the delivery boy ID
//	        UUID deliveryBoyId = orderEntity.getDeliveryBoyId();
//	        if (deliveryBoyId == null) {
//	            throw new RuntimeException("Delivery boy is not assigned to this order");
//	        }
//
//	        // Fetch delivery boy details from the User table
//	        User deliveryBoy = userRepo.findById(deliveryBoyId)
//	                .orElseThrow(() -> new RuntimeException("Delivery boy details not found"));
//
//	        // Generate a dynamic 6-digit OTP
//	        String dynamicOtp = generateRandomOtp();
//
//	        // Instead of calling sendOtp(), directly return the dynamic OTP response
//	        OtpResponse otpResponse = new OtpResponse();
//	        otpResponse.setType(dynamicOtp);
//	        otpResponse.setMessage("OTP has been sent successfully");
//
//	        return otpResponse;
//	    }

	    // Utility method to generate a random 6-digit OTP
	    private String generateRandomOtp() {
	        Random random = new Random();
	        int otp = 100000 + random.nextInt(900000);  // Generate a random 6-digit number
	        return String.valueOf(otp);
	    }

	    @Override
	    public OtpResponse verifyOtp(String mobileNumber, int otp) throws Exception{
	        OtpResponse otpResponse = new OtpResponse();

	        // Static OTP for testing (can be changed as needed)
	        int staticOtp = 123456;

	        // Verify the OTP
	        if (otp == staticOtp) {
	            otpResponse.setType("success");
	            otpResponse.setMessage("OTP verified successfully.");
	        } else {
	            otpResponse.setType("failure");
	            otpResponse.setMessage("Invalid OTP.");
	        }

	        return otpResponse;
	    }

	    
	    @Override
	    public void confirmDelivery(String orderId) throws Exception {
	        OrderEntity orderEntity = orderRepository.findById(UUID.fromString(orderId))
	                .orElseThrow(() -> new RuntimeException("Order not found"));

	        // Check if the order is already delivered
	        if (orderEntity.getOrderstatus() == OrderStatus.DELIVERED) {
	            throw new RuntimeException("Order already delivered");
	        }

	        // Update order status to DELIVERED
	        orderEntity.setOrderstatus(OrderStatus.DELIVERED);
	        orderRepository.save(orderEntity);
	    }

	    
//	    @Override
//	    public List<OrderRequest> getTodayOrders() {
//
//	        LocalDate today = LocalDate.now();
//	        List<OrderEntity> orders = orderRepository.findByOrderDate(today);
//
//	        if (orders.isEmpty()) {
//	            throw new RuntimeException("No orders found today");
//	        }
//
//	        return orders.stream()
//	                .map(order -> OrderRequest.builder()
//	                        .orderId(order.getId().toString())
//	                        .customerName(order.getCustomerName())
//	                        .deliveryAddress(order.getDeliveryAddress())
//	                        .contactNumber(order.getContactNumber())
//	                        .orderstatus(order.getOrderstatus().toString())
//	                        .build())
//	                .collect(Collectors.toList());
//	    }

}
