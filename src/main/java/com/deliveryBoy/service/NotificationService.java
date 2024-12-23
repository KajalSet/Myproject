package com.deliveryBoy.service;



import java.util.List;

import com.deliveryBoy.entity.NotificationEntity;
import com.deliveryBoy.entity.OrderEntity;
import com.deliveryBoy.entity.ProfileEntity;


public interface NotificationService {

	void createNotification(ProfileEntity profile, OrderEntity order, String brief);

	void markAsRead(Long notificationId);

	List<NotificationEntity> fetchNotifications(Long profileId, Boolean readStatus);

	
}
