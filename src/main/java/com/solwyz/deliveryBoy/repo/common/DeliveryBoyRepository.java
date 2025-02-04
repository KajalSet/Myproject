package com.solwyz.deliveryBoy.repo.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solwyz.deliveryBoy.models.DeliveryBoy;

@Repository
public interface DeliveryBoyRepository extends JpaRepository<DeliveryBoy, Long> {

	DeliveryBoy findByUsername(String username);

}
