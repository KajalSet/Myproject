package com.deliveryBoy.entity;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.enums.OrderStatus;


import lombok.Data;



@Data
@Entity
@Table(name="HomePage")
public class HomeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;
	
	private String orderId;
	
	private String customerName;
	
	private String deliveryAddress;
	
	private String contactNumber;
	
	private String status;  //Accepted,In Transit
	
	private LocalDate orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderstatus;
	

	private String rejection;
	
	@Enumerated(EnumType.STRING)
	private AvailabilityStatus availabilityStatus;
	
	private UUID deliveryBoyId; 

}
