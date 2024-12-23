package com.deliveryBoy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryBoy.entity.NotificationEntity;
import com.deliveryBoy.entity.OrderEntity;
import com.deliveryBoy.entity.ProfileEntity;
import com.deliveryBoy.service.NotificationService;
import com.deliveryBoy.service.OrderService;
import com.deliveryBoy.service.ProfileService;

@RestController
@RequestMapping("/api/")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private OrderService orderService;

	@PostMapping("/create")
	public ResponseEntity<String> triggerNotification(@RequestParam Long profileId, @RequestParam Long orderId,
			@RequestParam String brief) {

		ProfileEntity profile = profileService.getProfileById(orderId);
		OrderEntity order = orderService.getOrderById(orderId);

		if (profile == null) {
			return ResponseEntity.badRequest().body("Invalid profile ID");
		}

		if (order == null) {
			return ResponseEntity.badRequest().body("Invalid order ID");
		}

		String.format("Order #%d for customer %s: %s", orderId, order.getCustomerName(), brief);

		notificationService.createNotification(profile, order, brief);

		return ResponseEntity.ok("Notification triggered successfully");

	}//
	

	@PutMapping("/{notificationId}/markAsRead")
	public ResponseEntity<String> markNotificationAsRead(@PathVariable Long notificationId) {
		notificationService.markAsRead(notificationId);
		return ResponseEntity.ok("Notification marked as read.");
	}//

	
	@GetMapping("/{profileId}")
	public ResponseEntity<List<NotificationEntity>> getNotifications(@PathVariable Long profileId,
			@RequestParam(required = false) Boolean readStatus) {
		List<NotificationEntity> notifications = notificationService.fetchNotifications(profileId, readStatus);
		return ResponseEntity.ok(notifications);
	}

}
