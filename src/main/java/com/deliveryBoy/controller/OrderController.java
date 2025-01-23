package com.deliveryBoy.controller;

import com.deliveryBoy.entity.OrderEntity;
import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.enums.RejectOrderReason;
import com.deliveryBoy.request.OrderRequest;
import com.deliveryBoy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "*")
@RestController	
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @PostMapping("/create") //tested 
    public ResponseEntity<OrderRequest> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderEntity orderEntity = orderService.createOrder(orderRequest);  // Call service method to create order

        OrderRequest createdOrderRequest = OrderRequest.builder()
                .orderId(orderEntity.getId().toString())  // UUID generated automatically, converted to string
                .customerName(orderEntity.getCustomerName())
                .deliveryAddress(orderEntity.getDeliveryAddress())
                .contactNumber(orderEntity.getContactNumber())
                .orderstatus(orderEntity.getOrderstatus().name()) // Convert enum to String
                .availabilityStatus(orderEntity.getAvailabilityStatus().name()) // Convert enum to String
                .build();
        
        return ResponseEntity.status(201).body(createdOrderRequest);  // Return the created order in response
    }

    
    
    // id orderstatus available null
    @GetMapping("/today") //tested 
    public ResponseEntity<List<OrderRequest>> getTodayOrders() {
        List<OrderRequest> orders = orderService.getTodayOrders();
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/allOrder")
    public ResponseEntity<List<OrderRequest>> getAllOrders() {
        List<OrderRequest> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    
    
    @GetMapping("/canceled-orders")
    public ResponseEntity<List<OrderRequest>> CanceledOrders() {
        List<OrderRequest> orders = orderService.canceledOrders();
        return ResponseEntity.ok(orders);
    }
    

    @PostMapping("/{orderId}/pickup")
    public ResponseEntity<String> pickupOrder(@PathVariable String orderId) {
        orderService.pickupOrder(orderId);
        return ResponseEntity.ok("Order picked up successfully");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // Endpoint to get orders by status
    @GetMapping("/status/{status}") //tested 
    public ResponseEntity<List<OrderRequest>> getOrdersByStatus(@PathVariable String status) {
        List<OrderRequest> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    // Endpoint to accept an order
    @PostMapping("/{orderId}/accept")
    public ResponseEntity<String> acceptOrder(@PathVariable String orderId) {
        orderService.acceptOrder(orderId);
        return ResponseEntity.ok("Order accepted successfully");
    }

    // Endpoint to reject an order
    @PostMapping("/{orderId}/reject")
    public ResponseEntity<String> rejectOrder(@PathVariable String orderId, 
                                              @RequestParam RejectOrderReason reason) {
        orderService.rejectOrder(orderId, reason);
        return ResponseEntity.ok("Order rejected successfully");
    }

    // Endpoint to toggle the availability of a delivery boy
    @PostMapping("/{deliveryBoyId}/toggle-availability")
    public ResponseEntity<String> toggleAvailability(@PathVariable UUID deliveryBoyId, 
                                                     @RequestParam AvailabilityStatus status) {
        orderService.toggleAvailability(deliveryBoyId, status);
        return ResponseEntity.ok("Availability status updated");
    }

    // Endpoint to get the count of new orders
    @GetMapping("/new/count")
    public ResponseEntity<Integer> getNewOrdersCount() {
        int count = orderService.getNewOrdersCount();
        return ResponseEntity.ok(count);
   }
    
    
    
    
    //todayorders-picked the order
    //getallorders
    //cancelorders
    
    
    
}
