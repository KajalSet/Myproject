package com.deliveryBoy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name="user_profiles")
public class ProfileEntity {

	@Id
	private Long userid;
	private String name;
	private String phoneNumber;
	
	@Lob
	private byte[] profilePhoto;
}
