package com.deliveryBoy.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryBoy.entity.OrderEntity;
import com.deliveryBoy.enums.CancellationReason;
import com.deliveryBoy.enums.OrderStatus;
import com.deliveryBoy.request.CancellationRequest;
import com.deliveryBoy.request.OTPVerificationRequest;
import com.deliveryBoy.request.OrderRequest;
import com.deliveryBoy.request.OrderStatusRequest;
import com.deliveryBoy.request.PaymentStatusRequest;
import com.deliveryBoy.response.CustomerAddressResponse;
import com.deliveryBoy.response.OrderResponse;
import com.deliveryBoy.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/category")
	public ResponseEntity<List<OrderResponse>> getOrdersByCategory(@RequestParam("category") String category) {
		try {
			List<OrderResponse> orders = orderService.getOrdersByCategory(category);
			return ResponseEntity.ok(orders);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(Collections.emptyList());
		}

	}

	@PutMapping("/{orderId}/status")
	public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId,
			@RequestBody OrderStatusRequest statusRequest, @RequestParam(required = false) String updatedBy) {

		try {

			if (updatedBy == null || updatedBy.isEmpty()) {
				updatedBy = "System";
			}
			orderService.updateOrderStatus(orderId, OrderStatus.valueOf(statusRequest.getStatus().toUpperCase()),
					updatedBy);
			return ResponseEntity.ok("Order status updated successfully.");

		} catch (IllegalArgumentException e) {

			return ResponseEntity.badRequest().body("Invalid order status.");
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while updating the order status.");
		}
	}// update status

	@PostMapping("/{orderId}/verify-otp")
	public ResponseEntity<String> verifyOtpAndMarkDelivered(@PathVariable Long orderId,
			@RequestBody OTPVerificationRequest otpRequest) {

		try {
			orderService.verifyOtpAndMarkDelivered(orderId, otpRequest.getOtp());

			return ResponseEntity.ok("Order successfully delivered.");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred during OTP verification.");
		}
	}//

	@PostMapping("/orders")
	public ResponseEntity<String> saveOrder(@RequestBody OrderRequest orderRequest) {
		try {
			orderService.saveOrder(orderRequest);
			return ResponseEntity.status(HttpStatus.CREATED).body("Order saved successfully.");

		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Invalid payment mode or order status.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while saving the order.");
		}

	}//

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {

		try {
			OrderEntity order = orderService.getOrderById(orderId);
			return ResponseEntity.ok(OrderResponse.fromEntity(order));
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}//

	@GetMapping("/orders/{orderId}/address")
	public ResponseEntity<CustomerAddressResponse> getDeliveryAddressByOrderId(@PathVariable Long orderId) {
		try {
			OrderEntity order = orderService.getOrderById(orderId);
			CustomerAddressResponse addressResponse = CustomerAddressResponse.fromAddress(order.getDeliveryAddress());
			return ResponseEntity.ok(addressResponse);
		} catch (EntityNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}//

	// view exact address pending

	@PostMapping("/{orderId}/progress-status")
	public ResponseEntity<String> progressOrderStatus(@PathVariable Long orderId,
			@RequestBody OrderStatusRequest statusRequest) {
		try {
			// Transition the order status to the next stage
			OrderStatus currentStatus = OrderStatus.valueOf(statusRequest.getStatus().toUpperCase());
			orderService.progressOrderStatus(orderId, currentStatus);

			return ResponseEntity.ok("Order status progressed successfully.");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Invalid status value.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error while progressing order status.");
		}
	}

	// approximate reaching location deliveryBoy

	@GetMapping("/cancellation-reasons")
	public ResponseEntity<List<String>> getCancellationReasons() {

		List<String> reasons = Arrays.stream(CancellationReason.values())
				.map(CancellationReason::getReason)
				.collect(Collectors.toList());
		;
		return ResponseEntity.ok(reasons);
	}

	@PostMapping("/{orderId}/cancel")
	public ResponseEntity<String> cancelOrder(@PathVariable Long orderId,
			@RequestBody CancellationRequest cancellationRequest) {

		if (!CancellationReason.isValidReason(cancellationRequest.getReason())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid cancellation reason.");
		}

		boolean isCancelled = orderService.cancelOrder(orderId, cancellationRequest.getReason());

		if (isCancelled) {
			return ResponseEntity.ok("Order cancelled successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order cancellation failed.");
		}
	}//

	@PutMapping("/updatePaymentStatus")
	public ResponseEntity<String> updatePaymentStatus(@RequestBody PaymentStatusRequest request) {
		try {
			orderService.updatePaymentStatus(request);
			return ResponseEntity.ok("Payment status updated successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
