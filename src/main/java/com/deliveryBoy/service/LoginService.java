package com.deliveryBoy.service;

import org.springframework.http.ResponseEntity;

import com.deliveryBoy.entity.LoginEntity;
import com.deliveryBoy.request.LoginRequest;

public interface LoginService {

	ResponseEntity<String> login(String username, String password);

	boolean isMpinCreated(String userName);

	boolean authenticate(String userName, String password);

	String register(LoginRequest loginRequest);

	LoginEntity createMpin(Long id, Integer mpin);

	LoginEntity resetMpin(Long id, Integer newMpin);

	LoginEntity save(LoginEntity loginEntity);

	boolean validateMpin(Long id, Integer mpin);

	boolean updatePassword(String phoneNumber, String newPassword);

}
