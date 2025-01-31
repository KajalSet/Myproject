package com.deliveryBoy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.deliveryBoy.auth.User;


public interface UserRepository extends JpaRepository<User, UUID>,JpaSpecificationExecutor<User> {

	User findByMobileNumber(String phone);
	
	//User findByMobileNumber(String phone);
}
