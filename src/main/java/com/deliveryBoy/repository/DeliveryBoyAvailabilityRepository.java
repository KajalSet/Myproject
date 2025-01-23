package com.deliveryBoy.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliveryBoy.entity.DeliveryBoyAvailability;

@Repository
public interface DeliveryBoyAvailabilityRepository extends JpaRepository<DeliveryBoyAvailability, UUID> {
    Optional<DeliveryBoyAvailability> findByDeliveryBoyId(UUID deliveryBoyId);
}
