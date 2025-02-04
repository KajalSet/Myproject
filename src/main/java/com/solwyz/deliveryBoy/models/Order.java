package com.solwyz.deliveryBoy.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String address;
	private String status; // e.g., "accepted", "delivered", "cancelled"

	@ManyToOne
	@JoinColumn(name = "delivery_boy_id")
	private DeliveryBoy deliveryBoy;

}
