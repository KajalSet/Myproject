//package com.deliveryBoy.entity;
//
//import java.time.LocalDateTime;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import lombok.Data;
//
//@Data
//@Entity
//@Table(name="Notification")
//
//public class NotificationEntity {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	
//	private String customerName;
//	private String Brief;
//	private LocalDateTime timestamp;
//	private boolean isread;
//	
//	@ManyToOne
//	private OrderEntity order;
//	
//	@ManyToOne
//	private ProfileEntity profileEntity;
//	
//}
