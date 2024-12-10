package com.deliveryBoy.service;

import org.springframework.stereotype.Service;

import com.deliveryBoy.entity.LoginEntity;
import com.deliveryBoy.request.LoginRequest;


public interface LoginService {



	boolean login(String username, String password);

	boolean isMpinCreated(String userName);

	void createMpin(String username, String mpin);

	String register(LoginRequest loginRequest);

	

	



	 

	

}
