package com.deliveryBoy.entity;


import lombok.Data;

@Data
@Entity
@Table(name="HomePage")
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
