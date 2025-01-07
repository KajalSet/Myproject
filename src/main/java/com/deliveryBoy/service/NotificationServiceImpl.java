//package com.deliveryBoy.service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import javax.persistence.EntityNotFoundException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.deliveryBoy.entity.NotificationEntity;
//import com.deliveryBoy.entity.OrderEntity;
//import com.deliveryBoy.entity.ProfileEntity;
//import com.deliveryBoy.repository.NotificationRepository;
//
//@Service
//public class NotificationServiceImpl implements NotificationService {
//
//	@Autowired
//	private NotificationRepository notificationRepository;
//
//	@Autowired
//	ProfileService profileService;
//
//	@Override
//	public void createNotification(ProfileEntity profile, OrderEntity order, String brief) {
//		NotificationEntity notification = new NotificationEntity();
//		notification.setProfileEntity(profile);
//		notification.setOrder(order);
//		notification.setCustomerName(order.getCustomerName());
//		notification.setBrief(brief);
//		notification.setTimestamp(LocalDateTime.now());
//		notification.setIsread(false);
//
//		notificationRepository.save(notification);
//
//	}//
//
//	@Override
//	public void markAsRead(Long notificationId) {
//
//		NotificationEntity notification = notificationRepository.findById(notificationId)
//				.orElseThrow(() -> new EntityNotFoundException("Notification not found for ID: " + notificationId));
//
//		notification.setIsread(true);
//		notificationRepository.save(notification);
//
//	}//
//
//	@Override
//	public List<NotificationEntity> fetchNotifications(Long profileId, Boolean readStatus) {
//
//		ProfileEntity profile = profileService.getProfileById(profileId); // Fetch profile
//
//		if (readStatus == null) {
//
//			return notificationRepository.findByProfileOrderByTimestampDesc(profile);
//		} 
//
//		// Fetch notifications based on read status
//		return notificationRepository.findByProfileAndReadStatusOrderByTimestampDesc(profile, readStatus);
//
//	}
//
//	@Override
//	public void sendNotification(Long long1, String string) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	
//
//}
