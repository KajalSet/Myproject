package com.deliveryBoy.controller;

import java.time.LocalDateTime;
import java.util.List;
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

import com.deliveryBoy.entity.OrderEntity;
import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.enums.OrderStatus;
import com.deliveryBoy.enums.RejectOrderReason;
import com.deliveryBoy.request.OrderRequest;
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
    
    
    @GetMapping("/today-orders")
    public ResponseEntity<List<OrderRequest>> getTodayOrders() {
        List<OrderRequest> orders = homeService.getTodayOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

//    @GetMapping("/orders-by-status/{status}")
//    public ResponseEntity<List<OrderRequest>> getOrdersByStatus(@PathVariable String status) {
//        try {
//            List<OrderRequest> orders = homeService.getOrdersByStatus(status);
//            if (orders.isEmpty()) {
//                return ResponseEntity.notFound().build(); // Return 404 if no orders are found
//            }
//            return ResponseEntity.ok(orders);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(null); // Handle invalid status
//        }
//    }

    @PutMapping("/accept-order/{orderId}")
    public ResponseEntity<Void> acceptOrder(@PathVariable String orderId) {
        homeService.acceptOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reject-order/{orderId}")
    public ResponseEntity<Void> rejectOrder(@PathVariable String orderId, @RequestParam RejectOrderReason reason) {
        homeService.rejectOrder(orderId, reason);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{deliveryBoyId}/availability")
    public AvailabilityStatus getAvailability(@PathVariable UUID deliveryBoyId) {
        return availabilityService.getAvailabilityStatus(deliveryBoyId);
    }

    @PutMapping("/{deliveryBoyId}/availability")
    public ResponseEntity<Void> toggleAvailability(@PathVariable UUID deliveryBoyId,
                                                    @RequestBody AvailabilityRequest request) {
        availabilityService.toggleAvailability(deliveryBoyId, request.getAvailabilityStatus());
        return ResponseEntity.ok().build();
    }

    public static class AvailabilityRequest {
        private AvailabilityStatus availabilityStatus;

        // Getters and setters
        public AvailabilityStatus getAvailabilityStatus() {
            return availabilityStatus;
        }

        public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
            this.availabilityStatus = availabilityStatus;
        }
    }

//    @GetMapping("/new-orders-count")
//    public ResponseEntity<Integer> getNewOrdersCount() {
//        int newOrdersCount = homeService.getNewOrdersCount();
//        return ResponseEntity.ok(newOrdersCount);
//    }

    @GetMapping("/allorders")
    public ResponseEntity<List<OrderEntity>> getAllOrders() {
        List<OrderEntity> orders = homeService.getAllOrders();
        return ResponseEntity.ok(orders); // Return all orders with 200 OK
    }

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
