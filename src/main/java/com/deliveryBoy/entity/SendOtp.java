package com.deliveryBoy.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SendOtp {

	
	private String mobile;
	private String fcmToken;
	private String username;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFcmToken() {
		return fcmToken;
	}
	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	
}
