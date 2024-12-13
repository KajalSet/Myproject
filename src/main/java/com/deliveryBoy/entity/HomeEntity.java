package com.deliveryBoy.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.enums.OrderStatus;
import com.deliveryBoy.enums.RejectOrderReason;

import lombok.Data;

@Data
@Entity
public class HomeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String orderId;
	
	private String customerName;
	
	private String deliveryAddress;
	
	private String contactNumber;
	
	private String status;  //Accepted,In Transit
	
	private LocalDate orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderstatus;
	
	@Enumerated(EnumType.STRING)
	private RejectOrderReason rejection;
	
	@Enumerated(EnumType.STRING)
	private AvailabilityStatus availabilityStatus;
	

}
