//package com.deliveryBoy.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import com.deliveryBoy.entity.NotificationEntity;
//import com.deliveryBoy.entity.ProfileEntity;
//
//@Repository
//public interface NotificationRepository extends JpaRepository<NotificationEntity,Long>{
//
//	List<NotificationEntity> findByProfileOrderByTimestampDesc(ProfileEntity profile);
//
//	List<NotificationEntity> findByProfileAndReadStatusOrderByTimestampDesc(ProfileEntity profile, Boolean readStatus);
//
//	List<NotificationEntity> findByProfileEntity(ProfileEntity profile);
// 
//}
