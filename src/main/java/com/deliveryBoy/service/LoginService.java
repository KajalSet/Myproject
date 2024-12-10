package com.deliveryBoy.service;

import org.springframework.stereotype.Service;

import com.deliveryBoy.entity.LoginEntity;
import com.deliveryBoy.request.LoginRequest;


public interface LoginService {



	boolean login(String username, String password);

	boolean isMpinCreated(String userName);

	String createMpin(String username, Integer mpin);

	String register(LoginRequest loginRequest);

	

	



	 

	

}
