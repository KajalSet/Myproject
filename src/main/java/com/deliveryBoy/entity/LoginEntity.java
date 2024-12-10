package com.deliveryBoy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "DeliveryBoy")
public class LoginEntity {
@Id
@GeneratedValue(strategy =GenerationType.IDENTITY)
private Long id;

@Column(name="UserName",unique=true)
private String userName;

@Column(name="Password")
private String password;

@Column(name="Mpin")
private Integer mpin;

@Column(name = "isMpinCreated")
private boolean isMpinCreated;
	
	
	
	
	
}
