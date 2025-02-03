package com.deliveryBoy.request;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MPin {
	private UUID userId;
	private String mpin;
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public String getMpin() {
		return mpin;
	}
	public void setMpin(String mpin) {
		this.mpin = mpin;
	}
	
	

}
