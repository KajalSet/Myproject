package com.deliveryBoy.service;

import org.springframework.stereotype.Service;

import com.deliveryBoy.entity.LoginEntity;
import com.deliveryBoy.request.LoginRequest;


public interface LoginService {



	boolean login(String username, String password);

	boolean isMpinCreated(String userName);


	String register(LoginRequest loginRequest);

	LoginEntity createMpin(Long id, Integer mpin);

	LoginEntity resetMpin(Long id, Integer newMpin);

	

	



	 

	

}
