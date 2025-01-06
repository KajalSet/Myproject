//package com.deliveryBoy.entity;
//
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Lob;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//import lombok.Builder;
//import lombok.Data;
//
//@Data
//@Builder
//@Entity
//@Table(name="user_profiles")
//public class ProfileEntity {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY) 
//	private Long userid;
//	private String name;
//	private String phoneNumber;
//	
//	@Lob
//	private byte[] profilePhoto;
//	
//	@OneToMany(mappedBy = "profileEntity", cascade = CascadeType.ALL)
//    private List<NotificationEntity> notifications;
//}
