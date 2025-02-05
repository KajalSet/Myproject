package com.solwyz.deliveryBoy.service.common;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solwyz.deliveryBoy.models.DeliveryBoy;
import com.solwyz.deliveryBoy.models.Order;
import com.solwyz.deliveryBoy.repositories.common.DeliveryBoyRepository;
import com.solwyz.deliveryBoy.repositories.common.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private DeliveryBoyRepository deliveryBoyRepository;

	// Create Order
	public Order createOrder(Order order) {
		order.setStatus("PENDING");
		return orderRepository.save(order);
	}

	// Accept Order
	public Order acceptOrder(Long orderId, Long deliveryBoyId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		if (!order.getStatus().equals("PENDING")) {
			throw new RuntimeException("Order already processed");
		}

		DeliveryBoy deliveryBoy = deliveryBoyRepository.findById(deliveryBoyId)
				.orElseThrow(() -> new RuntimeException("Delivery Boy not found"));

		order.setStatus("ACCEPTED");
		order.setDeliveryBoy(deliveryBoy);
		return orderRepository.save(order);
	}

	// Reject Order
	public Order rejectOrder(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		if (!order.getStatus().equals("PENDING")) {
			throw new RuntimeException("Order already processed");
		}

		order.setStatus("REJECTED");
		return orderRepository.save(order);
	}

	// Get Orders Assigned to a Delivery Boy
	public List<Order> getOrdersByDeliveryBoy(Long deliveryBoyId, String status) {
		return orderRepository.findByDeliveryBoyIdAndStatus(deliveryBoyId, status);
	}

	// Get All Pending Orders
	public List<Order> getPendingOrders() {
		return orderRepository.findByStatus("PENDING");
	}



	// Get orders by date range (day/week/month/year)
	public List<Order> getOrdersByDateRange(Date startDate, Date endDate) {
		return orderRepository.findByOrderDateBetween(startDate, endDate);
	}

}
