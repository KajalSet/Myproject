package com.solwyz.deliveryBoy.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String address;
	private String status; // e.g., "accepted", "delivered", "cancelled"

	@ManyToOne
	@JoinColumn(name = "delivery_boy_id")
	private DeliveryBoy deliveryBoy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DeliveryBoy getDeliveryBoy() {
		return deliveryBoy;
	}

	public void setDeliveryBoy(DeliveryBoy deliveryBoy) {
		this.deliveryBoy = deliveryBoy;
	}

}
