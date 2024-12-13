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
	public LoginEntity createMpin(Long id, Integer mpin) {
		LoginEntity loginEntity=loginRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found!"));
		
		if(mpin.toString().length()!=4) {
			throw new IllegalArgumentException("Mpin must be a 4 digit number!");
		}
		loginEntity.setMpin(passwordEncoder.encode(mpin.toString()));
		loginEntity.setMpinCreated(true);
		
		return loginRepository.save(loginEntity);
	}




	@Override
	public LoginEntity resetMpin(Long id, Integer newMpin) {
		LoginEntity loginEntity=loginRepository.findById(id)
				.orElseThrow(()->new IllegalArgumentException("User Not Found!"));
		
		if(newMpin.toString().length()!=4) {
			throw new IllegalArgumentException("MPIN must be a 4digit number!");
		}
		loginEntity.setMpin(passwordEncoder.encode(newMpin.toString()));
		return loginRepository.save(loginEntity);
	}

	
	
	



	


	
	

	
	
}
