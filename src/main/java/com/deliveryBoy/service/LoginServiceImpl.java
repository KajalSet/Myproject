package com.deliveryBoy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deliveryBoy.entity.LoginEntity;
import com.deliveryBoy.repository.LoginRepository;
import com.deliveryBoy.request.LoginRequest;


@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	 LoginRepository loginRepository;
	@Autowired
	 PasswordEncoder passwordEncoder;
	

	
	@Override
	public String register(LoginRequest loginRequest) {
		
		if(loginRepository.findByUserName(loginRequest.getUserName()).isPresent()) {
			return "Username already exists";
			}
		
		LoginEntity loginEntity = new LoginEntity();
		loginEntity.setUserName(loginRequest.getUserName());
	    loginEntity.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
		
		loginRepository.save(loginEntity);
		return "User registered sucessfully";
	}
	
	
	
	
	@Override
	public boolean login(String username, String password) {
	
		Optional<LoginEntity> dblog =loginRepository.findByUserName(username);
		if(dblog.isPresent()) {
			LoginEntity loginEntity = dblog.get();
			return passwordEncoder.matches(password, loginEntity.getPassword());
		}
		
		return false;
	}//login
	
	

	@Override
	public boolean isMpinCreated(String userName) {
	    return loginRepository.findByUserName(userName)
	                          .map(LoginEntity::isMpinCreated)
	                          .orElse(false);
	}//ismpincreated

	
	@Override
	public String createMpin(String username, Integer mpin) {

	    Optional<LoginEntity> userOptional = loginRepository.findByUserName(username);

	    if (!userOptional.isPresent()) {
	        
	        throw new UsernameNotFoundException("User not found for username: " + username);
	    }

	    
	    LoginEntity user = userOptional.get();
	    
	    if (user == null) {
	        throw new UsernameNotFoundException("User not found for username: " + username);
	    }

	    user.setMpin(mpin);
	    user.setMpinCreated(true);

	    loginRepository.save(user);
	    return "MPIN created successfully.";
	}//create mpin

	



	


	
	

	
	
}
