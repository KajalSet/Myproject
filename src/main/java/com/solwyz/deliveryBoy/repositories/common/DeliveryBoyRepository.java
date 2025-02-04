package com.solwyz.deliveryBoy.repositories.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solwyz.deliveryBoy.models.DeliveryBoy;

@Repository
public interface DeliveryBoyRepository extends JpaRepository<DeliveryBoy, Long> {
	// Custom method to find a DeliveryBoy by their username
	DeliveryBoy findByUsername(String username);

}
