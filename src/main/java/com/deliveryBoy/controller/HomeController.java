package com.deliveryBoy.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryBoy.auth.User;
import com.deliveryBoy.auth.UserRepo;
import com.deliveryBoy.entity.DeliveryBoyAvailability;
import com.deliveryBoy.entity.OrderEntity;
import com.deliveryBoy.entity.OtpResponse;
import com.deliveryBoy.entity.VerifyOtpRequest;
import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.enums.OrderStatus;

import com.deliveryBoy.repository.DeliveryBoyAvailabilityRepository;
import com.deliveryBoy.request.OrderRequest;
import com.deliveryBoy.response.ApiResponse;
import com.deliveryBoy.service.DeliveryBoyAvailabilityService;
import com.deliveryBoy.service.HomeService;
import com.deliveryBoy.service.OrderService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/home")
public class HomeController {
	@Autowired
    private HomeService homeService;

    @Autowired
    private DeliveryBoyAvailabilityService availabilityService;

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private DeliveryBoyAvailabilityRepository availabilityRepository;
    
    @Autowired
    UserRepo userRepo;
    
    
    @GetMapping("/today-orders")
    public ResponseEntity<ApiResponse<List<OrderRequest>>> getTodayOrders() {
        List<OrderRequest> orders = homeService.getTodayOrders();
        return ResponseEntity.ok(new ApiResponse<>(true, orders));
    }

    @PutMapping("/accept-order/{orderId}")
    public ResponseEntity<ApiResponse<Void>> acceptOrder(@PathVariable String orderId) {
        homeService.acceptOrder(orderId);
        return ResponseEntity.ok(new ApiResponse<>(true, null));
    }
  
    
    
    @PutMapping("/reject-order/{orderId}")
    public ResponseEntity<ApiResponse<Void>> rejectOrder(@PathVariable String orderId, @RequestParam String reason) {
        homeService.rejectOrder(orderId, reason);
        return ResponseEntity.ok(new ApiResponse<>(true, null));
    }

    @GetMapping("/{deliveryBoyId}/availability")
    public ResponseEntity<ApiResponse<AvailabilityStatus>> getAvailability(@PathVariable UUID deliveryBoyId) {
        AvailabilityStatus status = availabilityService.getAvailabilityStatus(deliveryBoyId);
        return ResponseEntity.ok(new ApiResponse<>(true, status));
    }
    

    @PostMapping("/addAvailability")
    public ResponseEntity<ApiResponse<DeliveryBoyAvailability>> addDeliveryBoyAvailability(@RequestBody DeliveryBoyAvailability availability) {
        // Save the DeliveryBoyAvailability entity to the database
        DeliveryBoyAvailability savedAvailability = availabilityRepository.save(availability);
        // Return the response in the desired format
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, savedAvailability));
    }
    
    
    
    @PutMapping("/{deliveryBoyId}/availability")
    public ResponseEntity<ApiResponse<String>> toggleAvailability(@PathVariable UUID deliveryBoyId,
                                                                  @RequestBody AvailabilityRequest request) {
        
        availabilityService.toggleAvailability(deliveryBoyId, request.getAvailabilityStatus());

       
        String responseMessage = "Availability status updated to " + request.getAvailabilityStatus();
        return ResponseEntity.ok(new ApiResponse<>(true, responseMessage));
    }
    

    @GetMapping("/allorders")
    public ResponseEntity<ApiResponse<List<OrderEntity>>> getAllOrders() {
        List<OrderEntity> orders = homeService.getAllOrders();
        return ResponseEntity.ok(new ApiResponse<>(true, orders));
    }

    public static class AvailabilityRequest {
        private AvailabilityStatus availabilityStatus;

        public AvailabilityStatus getAvailabilityStatus() {
            return availabilityStatus;
        }

        public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
            this.availabilityStatus = availabilityStatus;
        }
    }
    
    
    
    
    @GetMapping("/user/address")
    public ResponseEntity<?> getUserDeliveryAddress(@RequestParam("userId") UUID userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            String deliveryAddress = user.get().getCurrentLocation();  // Fetch delivery address (currentLocation)
            return ResponseEntity.ok(new ApiResponse<>(true,deliveryAddress));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(new ApiResponse<>(false, "User not found"));
        }
    }
    
//DBoy accept the order otp is send to his mobile no.
    
    @PutMapping("/send-otp-delivery/{orderId}")
    public ResponseEntity<OtpResponse> sendOtpToDeliveryBoy(@PathVariable String orderId) throws Exception {
        OtpResponse otpResponse = homeService.sendOtpToDeliveryBoy(orderId);
        return ResponseEntity.ok(otpResponse);
    }
   
    
    
    @PostMapping("/verifyotp")
    public ResponseEntity<OtpResponse> verifyOtp(@RequestBody VerifyOtpRequest verifyOtpRequest) throws Exception {
        OtpResponse otpResponse = homeService.verifyOtp(verifyOtpRequest.getMobileNumber(), verifyOtpRequest.getOtp());
        return ResponseEntity.ok(otpResponse);
    }
    
    
  //confirm delivery after verifying otp
    @PutMapping("/confirm-delivery/{orderId}")
    public ResponseEntity<ApiResponse<String>> confirmDelivery(@PathVariable String orderId) throws Exception {
        homeService.confirmDelivery(orderId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Delivery Confirmed Successfully"));
    }
    
    
    
    
    
    
    
    
    
    
    

//    @PostMapping("/verify-otp")
//    public ResponseEntity<OtpResponse> verifyOtp(@RequestBody VerifyOtpRequest verifyOtpRequest) throws Exception {
//        if (verifyOtpRequest == null || verifyOtpRequest.getMobileNumber() == null) {
//            return ResponseEntity.badRequest().body(new OtpResponse("failure", "Invalid request data."));
//        }
//        OtpResponse otpResponse = homeService.verifyOtp(verifyOtpRequest.getMobileNumber(), verifyOtpRequest.getOtp());
//        return ResponseEntity.ok(otpResponse);
//    }
    
//    @PostMapping("/verify-otp")
//    public ResponseEntity<OtpResponse> verifyOtp(@RequestBody VerifyOtpRequest verifyOtpRequest) throws Exception {
//        System.out.println("Incoming OTP verification request: " + verifyOtpRequest);
//        OtpResponse otpResponse = homeService.verifyOtp(verifyOtpRequest.getMobileNumber(), verifyOtpRequest.getOtp());
//        System.out.println("OTP verification response: " + otpResponse);
//        return ResponseEntity.ok(otpResponse);
//    }





    
    
    
    
//  @GetMapping("/new-orders-count")
//  public ResponseEntity<Integer> getNewOrdersCount() {
//      int newOrdersCount = homeService.getNewOrdersCount();
//      return ResponseEntity.ok(newOrdersCount);
//  }

//    @PostMapping("/createOrders")
//    public ResponseEntity<OrderEntity> saveOrder(@RequestBody OrderRequest orderRequest) {
//        // Convert OrderRequest to OrderEntity
//        OrderEntity orderEntity = new OrderEntity();
//        orderEntity.setCustomerName(orderRequest.getCustomerName());
//        orderEntity.setDeliveryAddress(orderRequest.getDeliveryAddress());
//        orderEntity.setContactNumber(orderRequest.getContactNumber());
//      
//        // Convert orderStatus (String) to OrderStatus enum
//        orderEntity.setOrderstatus(OrderStatus.valueOf(orderRequest.getOrderstatus())); // Set order status
//        orderEntity.setAvailabilityStatus(AvailabilityStatus.valueOf(orderRequest.getAvailabilityStatus())); // Set availabilityStatus
//        orderEntity.setCreatedAt(LocalDateTime.now());
//        try {
//            UUID deliveryBoyId = UUID.fromString(orderRequest.getDeliveryBoyId());
//            orderEntity.setDeliveryBoyId(deliveryBoyId); // Set the deliveryBoyId
//        } catch (IllegalArgumentException e) {
//            throw new RuntimeException("Invalid UUID format for deliveryBoyId: " + orderRequest.getDeliveryBoyId());
//        }
//
//        // Save the order
//        OrderEntity savedOrder = homeService.saveOrder(orderEntity);
//        return ResponseEntity.ok(savedOrder); // Return the saved order with 200 OK
//    }

 
    
    
}
