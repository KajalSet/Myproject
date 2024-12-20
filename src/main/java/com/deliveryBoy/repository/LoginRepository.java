package com.deliveryBoy.repository;

import java.util.Optional;

import org.springframework.boot.autoconfigure.jms.JmsProperties.DeliveryMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliveryBoy.entity.LoginEntity;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity,Long>{

	Optional<LoginEntity> findByUserName(String username);

	Optional<LoginEntity> findById(Long id);

	
}
