package com.deliveryBoy.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "DeliveryBoy")
public class LoginEntity {
@Id
@GeneratedValue(strategy =GenerationType.IDENTITY)
private Long id;

//@OneToOne
//@JoinColumn(name = "user_id")
//private UserEntity user;
private String userName;

private String password;


//@Transient
private Integer mpin;

@Column(name = "isMpinCreated")
private boolean isMpinCreated=false;
	
	
}
