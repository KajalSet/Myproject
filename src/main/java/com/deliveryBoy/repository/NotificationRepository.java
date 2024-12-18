package com.deliveryBoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliveryBoy.entity.NotificationEntity;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity,Long>{
 
}
