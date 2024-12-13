package com.deliveryBoy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.AssertFalse;

import lombok.Data;

@Data
@Entity
@Table(name = "DeliveryBoy")
public class LoginEntity {
@Id
@GeneratedValue(strategy =GenerationType.IDENTITY)
private Long id;

@Column(nullable =false  ,name="UserName",unique=true)
private String userName;

@Column(name="Password",nullable =false)
private String password;

@Column(name="Mpin",nullable =false)
private String mpin;

@Column(name = "isMpinCreated")
private boolean isMpinCreated=false;
	
	
	
	
	
}
