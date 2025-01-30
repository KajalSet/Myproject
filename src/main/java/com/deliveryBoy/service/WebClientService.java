package com.deliveryBoy.service;

import com.deliveryBoy.entity.OtpResponse;

public interface WebClientService {
	
	OtpResponse checkAndcreateUser(String mobile, String fcmToken) throws Exception;
	OtpResponse verifyOTP(String mobile, int otp) throws Exception;
	OtpResponse reSendOTP(String mobile) throws Exception ;
	String sendOTP(String mobile) throws Exception;
	

}
