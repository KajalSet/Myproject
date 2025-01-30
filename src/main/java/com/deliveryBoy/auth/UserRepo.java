package com.deliveryBoy.auth;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, UUID>,JpaSpecificationExecutor<User>{

	 Optional<User> findByUserName(String username);

	User findByMobileNumber(String mobileNumber);
	

}
