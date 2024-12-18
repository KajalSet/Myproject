package com.deliveryBoy.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryBoy.entity.NotificationEntity;
import com.deliveryBoy.repository.NotificationRepository;
import com.deliveryBoy.request.NotificationRequest;

@Service
public class NoticationServiceImpl implements NotificationService {
    
	@Autowired
	private NotificationRepository notificationRepository;
	
	
	
	
	
	
}
