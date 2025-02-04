package com.solwyz.deliveryBoy.controller.auth;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solwyz.deliveryBoy.models.DeliveryBoy;
import com.solwyz.deliveryBoy.models.Order;
import com.solwyz.deliveryBoy.service.common.DeliveryBoyService;
import com.solwyz.deliveryBoy.service.common.OrderService;

import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "APIs for order related operations")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private DeliveryBoyService deliveryBoyService;

	// Get all orders (for admin)
	@GetMapping
	public List<Order> getAllOrders() {
		return orderService.getAllOrders();
	}

	// Get all orders assigned to a delivery boy
	@GetMapping("/my-orders")
	public List<Order> getOrdersForDeliveryBoy(@RequestParam String username) {
		DeliveryBoy deliveryBoy = deliveryBoyService.findByUsername(username);
		return orderService.getOrdersForDeliveryBoy(deliveryBoy);
	}

	// Accept an order
	@PostMapping("/accept")
	public ResponseEntity<Order> acceptOrder(@RequestParam Long orderId, @RequestParam String username) {
		DeliveryBoy deliveryBoy = deliveryBoyService.findByUsername(username);
		Order acceptedOrder = orderService.acceptOrder(orderId, deliveryBoy);
		return ResponseEntity.ok(acceptedOrder);
	}

	// Reject an order
	@PostMapping("/reject")
	public ResponseEntity<Order> rejectOrder(@RequestParam Long orderId) {
		Order rejectedOrder = orderService.rejectOrder(orderId);
		return ResponseEntity.ok(rejectedOrder);
	}

	// Mark an order as delivered
	@PostMapping("/deliver")
	public ResponseEntity<Order> deliverOrder(@RequestParam Long orderId) {
		Order deliveredOrder = orderService.markOrderAsDelivered(orderId);
		return ResponseEntity.ok(deliveredOrder);
	}

	// Get orders by status (for delivery boy)
	@GetMapping("/status")
	public List<Order> getOrdersByStatus(@RequestParam String username, @RequestParam String status) {
		DeliveryBoy deliveryBoy = deliveryBoyService.findByUsername(username);
		return orderService.getOrdersByStatus(deliveryBoy, status);
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
