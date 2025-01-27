package com.deliveryBoy.entity;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.deliveryBoy.enums.AvailabilityStatus;
import lombok.Data;

@Data
@Entity
@Table(name = "DeliveryBoyAvailability")
public class DeliveryBoyAvailability {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID deliveryBoyId; 
    
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;
}
