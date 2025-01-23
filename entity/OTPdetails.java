package com.deliveryBoy.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OTPdetails {
	
	private String otp;
    private LocalDateTime expirationTime;
}
