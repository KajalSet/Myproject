package com.deliveryBoy.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deliveryBoy.config.PasswordEncoderConfig;
import com.deliveryBoy.entity.LoginEntity;
import com.deliveryBoy.repository.LoginRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

	private final LoginRepository loginRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	@Override
	public String register(LoginEntity loginEntity) {
		if(loginRepository.findByUserName(loginEntity.getUserName()).isPresent()) {
			return "Username already exists";
		}
		loginEntity.setPassword(passwordEncoder.encode(loginEntity.getPassword()));
		
		loginRepository.save(loginEntity);
		
		return "User registered succesfully";
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
	Optional<LoginEntity> dblog2=loginRepository.findByUserName(userName);
		return dblog2.map(LoginEntity::isMpinCreated).orElse(false);
	}//ismpincreated

	
	
	
	@Override
	public void createMpin(String username, String mpin) {
	  Optional<LoginEntity> dblog3 = loginRepository.findByUserName(username);
		dblog3.ifPresent(LoginEntity->{
			LoginEntity.setMpin(passwordEncoder.encode(mpin));
			LoginEntity.setMpinCreated(true);
			loginRepository.save(LoginEntity);
		});
	}//creatempin



	
	
	

	
	
}
