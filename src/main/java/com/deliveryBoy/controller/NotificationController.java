package com.deliveryBoy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryBoy.entity.NotificationEntity;
import com.deliveryBoy.service.NotificationService;

@RestController
@RequestMapping("/api/")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;
	
	
	@GetMapping
    public List<NotificationEntity> getNotifications(@PathVariable Long deliveryBoyId) {
        return notificationService.getNotifications(deliveryBoyId);
    }

    // Create a new notification for a delivery boy
    @PostMapping
    public NotificationEntity createNotification(@PathVariable Long deliveryBoyId, @RequestBody NotificationEntity notification) {
        return notificationService.createNotification(
                deliveryBoyId,
                notification.getTitle(),
                notification.getMessage(),
                notification.getTimestamp(),
                notification.getOrder().getId()
        );
    }

    // Mark a notification as read
    @PutMapping("/{notificationId}/mark-as-read")
    public void markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
    }
	


}
