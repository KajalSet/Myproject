package com.deliveryBoy.controller;


import com.deliveryBoy.entity.OrderEntity;
import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.request.OrderRequest;
import com.deliveryBoy.response.ApiResponse;
import com.deliveryBoy.response.OrderResponse;
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
    public ResponseEntity<ApiResponse<OrderRequest>> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderEntity orderEntity = orderService.createOrder(orderRequest);

        OrderRequest createdOrderRequest = OrderRequest.builder()
                .orderId(orderEntity.getId().toString())  // UUID generated automatically, converted to string
                .customerName(orderEntity.getCustomerName())
                .deliveryAddress(orderEntity.getDeliveryAddress())
                .contactNumber(orderEntity.getContactNumber())
                .orderstatus(orderEntity.getOrderstatus().name()) // Convert enum to String
                .availabilityStatus(orderEntity.getAvailabilityStatus().name()) // Convert enum to String
                .build();

        return ResponseEntity.status(201).body(new ApiResponse<>(true, createdOrderRequest));  // Return the created order in response
    }

    @GetMapping("/allOrder")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(new ApiResponse<>(true, orders));
    }

    @GetMapping("/today-orders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getTodayOrders() {
        List<OrderResponse> orders = orderService.getTodayOrders();
        return ResponseEntity.ok(new ApiResponse<>(true, orders));
    }

    @GetMapping("/canceled-orders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getCanceledOrders() {
        List<OrderResponse> orders = orderService.canceledOrders();
        return ResponseEntity.ok(new ApiResponse<>(true, orders));
    }



    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<OrderRequest>>> getOrdersByStatus(@PathVariable String status) {
        List<OrderRequest> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(new ApiResponse<>(true, orders));
    }
//
//    @PostMapping("/{orderId}/accept")
//    public ResponseEntity<ApiResponse<String>> acceptOrder(@PathVariable String orderId) {
//        orderService.acceptOrder(orderId);
//        return ResponseEntity.ok(new ApiResponse<>(true, "Order accepted successfully"));
//    }
//
//    @PostMapping("/{orderId}/reject")
//    public ResponseEntity<ApiResponse<String>> rejectOrder(@PathVariable String orderId) {
//        orderService.rejectOrder(orderId);
//        return ResponseEntity.ok(new ApiResponse<>(true, "Order rejected successfully"));
//    }
//
//    @PostMapping("/{deliveryBoyId}/toggle-availability")
//    public ResponseEntity<ApiResponse<String>> toggleAvailability(@PathVariable UUID deliveryBoyId,
//                                                                  @RequestParam AvailabilityStatus status) {
//        orderService.toggleAvailability(deliveryBoyId, status);
//        return ResponseEntity.ok(new ApiResponse<>(true, "Availability status updated"));
//    }
//
//    @GetMapping("/new/count")
//    public ResponseEntity<ApiResponse<Integer>> getNewOrdersCount() {
//        int count = orderService.getNewOrdersCount();
//        return ResponseEntity.ok(new ApiResponse<>(true, count));
//    }
	

//	    
//	    @PostMapping("/create") //tested 
//	    public ResponseEntity<ApiResponse<OrderRequest>> createOrder(@RequestBody OrderRequest orderRequest) {
//	        OrderEntity orderEntity = orderService.createOrder(orderRequest);  // Call service method to create order
//
//	        OrderRequest createdOrderRequest = OrderRequest.builder()
//	                .orderId(orderEntity.getId().toString())  // UUID generated automatically, converted to string
//	                .customerName(orderEntity.getCustomerName())
//	                .deliveryAddress(orderEntity.getDeliveryAddress())
//	                .contactNumber(orderEntity.getContactNumber())
//	                .orderstatus(orderEntity.getOrderstatus().name()) // Convert enum to String
//	                .availabilityStatus(orderEntity.getAvailabilityStatus().name()) // Convert enum to String
//	                .build();
//	        
//	        return ResponseEntity.status(201).body(new ApiResponse<>(true, createdOrderRequest));  // Return the created order in response
//	    }
//	//
////	    @GetMapping("/today") //tested 
////	    public ResponseEntity<ApiResponse<List<OrderResponse>>> getTodayOrders() {
////	    	List<OrderResponse> orders=orderService.getTodayOrders();
////	        return ResponseEntity.ok(new ApiResponse<>(true, orders));
////	    }
//
//	    @GetMapping("/allOrder")
//	    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
//	    	List<OrderResponse> orders = orderService.getAllOrders();
//	        return ResponseEntity.ok(new ApiResponse<>(true, orders));
//	    }
//
////	    @GetMapping("/canceled-orders")
////	    public ResponseEntity<ApiResponse<List<OrderResponse>>> canceledOrders() {
////	    	List<OrderResponse> orders = orderService.canceledOrders();
////	        return ResponseEntity.ok(new ApiResponse<>(true, orders));
////	    }
//
//	    @PostMapping("/{orderId}/pickup")
//	    public ResponseEntity<ApiResponse<String>> pickupOrder(@PathVariable String orderId) {
//	        orderService.pickupOrder(orderId);
//	        return ResponseEntity.ok(new ApiResponse<>(true, "Order picked up successfully"));
//	    }
//
//	    @GetMapping("/status/{status}") //tested 
//	    public ResponseEntity<ApiResponse<List<OrderRequest>>> getOrdersByStatus(@PathVariable String status) {
//	        List<OrderRequest> orders = orderService.getOrdersByStatus(status);
//	        return ResponseEntity.ok(new ApiResponse<>(true, orders));
//	    }
//
//	    @PostMapping("/{orderId}/accept")
//	    public ResponseEntity<ApiResponse<String>> acceptOrder(@PathVariable String orderId) {
//	        orderService.acceptOrder(orderId);
//	        return ResponseEntity.ok(new ApiResponse<>(true, "Order accepted successfully"));
//	    }
//
////	    @PostMapping("/{orderId}/reject")
////	    public ResponseEntity<ApiResponse<String>> rejectOrder(@PathVariable String orderId, 
////	                                                           @RequestParam RejectOrderReason reason) {
////	        orderService.rejectOrder(orderId, reason);
////	        return ResponseEntity.ok(new ApiResponse<>(true, "Order rejected successfully"));
////	    }
//
//	    @PostMapping("/{deliveryBoyId}/toggle-availability")
//	    public ResponseEntity<ApiResponse<String>> toggleAvailability(@PathVariable UUID deliveryBoyId, 
//	                                                                  @RequestParam AvailabilityStatus status) {
//	        orderService.toggleAvailability(deliveryBoyId, status);
//	        return ResponseEntity.ok(new ApiResponse<>(true, "Availability status updated"));
//	    }
//
//	    @GetMapping("/new/count")
//	    public ResponseEntity<ApiResponse<Integer>> getNewOrdersCount() {
//	        int count = orderService.getNewOrdersCount();
//	        return ResponseEntity.ok(new ApiResponse<>(true, count));
//	    }
//
//	    @GetMapping("/today-orders")
//	    public ResponseEntity<ApiResponse<List<OrderRequest>>> getTodayOrders(
//	            @RequestParam(defaultValue = "ACCEPTED") String status) {
//	        List<OrderRequest> orders = orderService.getTodayOrders(status);
//	        return ResponseEntity.ok(new ApiResponse<>(true, orders));
//	    }
//	    
//	    @GetMapping("/canceledOrder")
//	    public ResponseEntity<ApiResponse<List<OrderRequest>>> getCanceledOrders(
//	            @RequestParam(defaultValue = "CANCELLED") String status) {
//	        List<OrderRequest> orders = orderService.getTodayOrders(status);
//	        return ResponseEntity.ok(new ApiResponse<>(true, orders));
//	    }
//	
//	
//	

//    @Autowired
//    private OrderService orderService;
//    
//    
//    @PostMapping("/create") //tested 
//    public ResponseEntity<ApiResponse<OrderRequest>> createOrder(@RequestBody OrderRequest orderRequest) {
//        OrderEntity orderEntity = orderService.createOrder(orderRequest);  // Call service method to create order
//
//        OrderRequest createdOrderRequest = OrderRequest.builder()
//                .orderId(orderEntity.getId().toString())  // UUID generated automatically, converted to string
//                .customerName(orderEntity.getCustomerName())
//                .deliveryAddress(orderEntity.getDeliveryAddress())
//                .contactNumber(orderEntity.getContactNumber())
//                .orderstatus(orderEntity.getOrderstatus().name()) // Convert enum to String
//                .availabilityStatus(orderEntity.getAvailabilityStatus().name()) // Convert enum to String
//                .build();
//        
//        return ResponseEntity.status(201).body(new ApiResponse<>(true, createdOrderRequest));  // Return the created order in response
//    }
//
////    @GetMapping("/today") //tested 
////    public ResponseEntity<ApiResponse<List<OrderResponse>>> getTodayOrders() {
////    	List<OrderResponse> orders=orderService.getTodayOrders();
////        return ResponseEntity.ok(new ApiResponse<>(true, orders));
////    }
//    
//    
//    @GetMapping("/today-orders")
//    public ResponseEntity<ApiResponse<List<OrderRequest>>> getTodayOrders(
//            @RequestParam(defaultValue = "ACCEPTED") String status) {
//        List<OrderRequest> orders = orderService.getTodayOrders(status);
//        return ResponseEntity.ok(new ApiResponse<>(true, orders));
//    }
//    
//    @GetMapping("/canceledOrder")
//    public ResponseEntity<ApiResponse<List<OrderRequest>>> getCanceledOrders(
//            @RequestParam(defaultValue = "CANCELLED") String status) {
//        List<OrderRequest> orders = orderService.getCanceledOrders(status);
//        return ResponseEntity.ok(new ApiResponse<>(true, orders));
//    }
//
//    @GetMapping("/allOrder")
//    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
//    	List<OrderResponse> orders = orderService.getAllOrders();
//        return ResponseEntity.ok(new ApiResponse<>(true, orders));
//    }
//
////    @GetMapping("/canceled-orders")
////    public ResponseEntity<ApiResponse<List<OrderResponse>>> canceledOrders() {
////    	List<OrderResponse> orders = orderService.canceledOrders();
////        return ResponseEntity.ok(new ApiResponse<>(true, orders));
////    }
//
////    @PostMapping("/{orderId}/pickup")
////    public ResponseEntity<ApiResponse<String>> pickupOrder(@PathVariable String orderId) {
////        orderService.pickupOrder(orderId);
////        return ResponseEntity.ok(new ApiResponse<>(true, "Order picked up successfully"));
////    }
//
//    @GetMapping("/status/{status}") //tested 
//    public ResponseEntity<ApiResponse<List<OrderRequest>>> getOrdersByStatus(@PathVariable String status) {
//        List<OrderRequest> orders = orderService.getOrdersByStatus(status);
//        return ResponseEntity.ok(new ApiResponse<>(true, orders));
//    }
//
//    @PostMapping("/{orderId}/accept")
//    public ResponseEntity<ApiResponse<String>> acceptOrder(@PathVariable String orderId) {
//        orderService.acceptOrder(orderId);
//        return ResponseEntity.ok(new ApiResponse<>(true, "Order accepted successfully"));
//    }
//
////    @PostMapping("/{orderId}/reject")
////    public ResponseEntity<ApiResponse<String>> rejectOrder(@PathVariable String orderId, 
////                                                           @RequestParam RejectOrderReason reason) {
////        orderService.rejectOrder(orderId, reason);
////        return ResponseEntity.ok(new ApiResponse<>(true, "Order rejected successfully"));
////    }
////
////    @PostMapping("/{deliveryBoyId}/toggle-availability")
////    public ResponseEntity<ApiResponse<String>> toggleAvailability(@PathVariable UUID deliveryBoyId, 
////                                                                  @RequestParam AvailabilityStatus status) {
////        orderService.toggleAvailability(deliveryBoyId, status);
////        return ResponseEntity.ok(new ApiResponse<>(true, "Availability status updated"));
////    }
//
//    @GetMapping("/new/count")
//    public ResponseEntity<ApiResponse<Integer>> getNewOrdersCount() {
//        int count = orderService.getNewOrdersCount();
//        return ResponseEntity.ok(new ApiResponse<>(true, count));
//    }
//
    
    
}
