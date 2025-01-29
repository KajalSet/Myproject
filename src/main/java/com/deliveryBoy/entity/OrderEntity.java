package com.deliveryBoy.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.apache.commons.lang3.RandomStringUtils;

import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.enums.OrderStatus;


import lombok.Data;

@Data
@Entity
@Table(name = "OrderPage")
public class OrderEntity {

	@Id
    
    private String id;
   
    private String customerName;
    private String deliveryAddress;
    private String contactNumber;
    private LocalDateTime createdAt;  // Created at timestamp
    private LocalDateTime updatedAt;

   
    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderstatus;  // You already have OrderStatus here

   
    private String rejection;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    private UUID deliveryBoyId;

    
    
    public static String generateOrderId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder orderId = new StringBuilder();

        for (int i = 0; i < 8; i++) {  // Generating 8 characters (modify if needed)
            orderId.append(chars.charAt(random.nextInt(chars.length())));
        }

        return orderId.toString();
    }

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = generateOrderId();
        }
    }

   
}
