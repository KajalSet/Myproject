package com.deliveryBoy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deliveryBoy.entity.LoginEntity;
import com.deliveryBoy.entity.RegisterEntity;

public interface RegisterRepository extends JpaRepository<RegisterEntity,Long> {

	Optional<LoginEntity> findByUserName(String userName);

	RegisterEntity findByMobileNumber(String mobileNumber);

}
