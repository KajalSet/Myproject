package com.solwyz.deliveryBoy.controller.auth;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solwyz.deliveryBoy.models.Order;
import com.solwyz.deliveryBoy.service.common.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	// Create Order (Admin or Customer)
	@PostMapping("/create")
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {
		return ResponseEntity.ok(orderService.createOrder(order));
	}

	// Accept Order (Delivery Boy)
	@PostMapping("/{orderId}/accept/{deliveryBoyId}")
	public ResponseEntity<Order> acceptOrder(@PathVariable Long orderId, @PathVariable Long deliveryBoyId) {
		return ResponseEntity.ok(orderService.acceptOrder(orderId, deliveryBoyId));
	}

	// Reject Order (Delivery Boy)
	@PostMapping("/{orderId}/reject")
	public ResponseEntity<Order> rejectOrder(@PathVariable Long orderId) {
		return ResponseEntity.ok(orderService.rejectOrder(orderId));
	}

	// Get Orders Assigned to Delivery Boy
	@GetMapping("/assigned/{deliveryBoyId}")
	public ResponseEntity<List<Order>> getAssignedOrders(@PathVariable Long deliveryBoyId,
			@RequestParam String status) {
		return ResponseEntity.ok(orderService.getOrdersByDeliveryBoy(deliveryBoyId, status));
	}

	// Get Pending Orders (Delivery Boys can see this)
	@GetMapping("/pending")
	public ResponseEntity<List<Order>> getPendingOrders() {
		return ResponseEntity.ok(orderService.getPendingOrders());
	}



	// Get orders by date range (filter by day, week, month, year)
	@GetMapping("/filter")
	public List<Order> getOrdersByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
		// Parse the startDate and endDate to Date objects, implement parsing logic as
		// needed
		Date start = new Date(startDate); // Use proper date parsing
		Date end = new Date(endDate); // Use proper date parsing
		return orderService.getOrdersByDateRange(start, end);
	}
}
