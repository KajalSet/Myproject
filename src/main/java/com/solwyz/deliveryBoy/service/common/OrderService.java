package com.solwyz.deliveryBoy.service.common;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.solwyz.deliveryBoy.models.DeliveryBoy;
import com.solwyz.deliveryBoy.models.Order;
import com.solwyz.deliveryBoy.repositories.common.OrderRepository;

public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private DeliveryBoyService deliveryBoyService;

	// Fetch all orders
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	// Fetch orders assigned to a specific delivery boy
	public List<Order> getOrdersForDeliveryBoy(DeliveryBoy deliveryBoy) {
		return orderRepository.findByDeliveryBoy(deliveryBoy);
	}

	// Accept an order
	public Order acceptOrder(Long orderId, DeliveryBoy deliveryBoy) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
		if (order.getStatus().equals("Pending")) {
			order.setStatus("Accepted");
			order.setDeliveryBoy(deliveryBoy);
			return orderRepository.save(order);
		} else {
			throw new RuntimeException("Order is already accepted or delivered");
		}
	}

	// Reject an order
	public Order rejectOrder(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
		if (order.getStatus().equals("Pending")) {
			order.setStatus("Rejected");
			return orderRepository.save(order);
		} else {
			throw new RuntimeException("Order is already accepted or delivered");
		}
	}

	// Mark order as delivered
	public Order markOrderAsDelivered(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
		if (order.getStatus().equals("Accepted")) {
			order.setStatus("Delivered");
			return orderRepository.save(order);
		} else {
			throw new RuntimeException("Order must be accepted first");
		}
	}

	// Get orders by status for a specific delivery boy
	public List<Order> getOrdersByStatus(DeliveryBoy deliveryBoy, String status) {
		return orderRepository.findByDeliveryBoyAndStatus(deliveryBoy, status);
	}

	// Get orders by date range (day/week/month/year)
	public List<Order> getOrdersByDateRange(Date startDate, Date endDate) {
		return orderRepository.findByOrderDateBetween(startDate, endDate);
	}

}
