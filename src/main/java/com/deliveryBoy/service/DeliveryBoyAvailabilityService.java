package com.deliveryBoy.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliveryBoy.entity.DeliveryBoyAvailability;
import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.repository.DeliveryBoyAvailabilityRepository;

@Service
public class DeliveryBoyAvailabilityService {

	@Autowired
    private DeliveryBoyAvailabilityRepository availabilityRepository;

    public AvailabilityStatus getAvailabilityStatus(UUID deliveryBoyId) {
        return availabilityRepository.findByDeliveryBoyId(deliveryBoyId)
                .map(DeliveryBoyAvailability::getAvailabilityStatus)
                .orElse(AvailabilityStatus.OFFLINE); // Default to OFFLINE if no record exists
    }

    public void toggleAvailability(UUID deliveryBoyId, AvailabilityStatus status) {
        DeliveryBoyAvailability availability = availabilityRepository.findByDeliveryBoyId(deliveryBoyId)
                .orElse(new DeliveryBoyAvailability());

        availability.setDeliveryBoyId(deliveryBoyId);
        availability.setAvailabilityStatus(status);

        availabilityRepository.save(availability);
    }

    public void addDeliveryBoyAvailability(UUID deliveryBoyId, AvailabilityStatus availabilityStatus) {
        DeliveryBoyAvailability availability = new DeliveryBoyAvailability();
        availability.setDeliveryBoyId(deliveryBoyId);
        availability.setAvailabilityStatus(availabilityStatus);

        // Save the new availability record
        availabilityRepository.save(availability); // Use the correct repository
    }
}
