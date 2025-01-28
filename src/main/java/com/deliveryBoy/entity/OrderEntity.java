package com.deliveryBoy.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.deliveryBoy.enums.AvailabilityStatus;
import com.deliveryBoy.enums.OrderStatus;


import lombok.Data;

@Data
@Entity
@Table(name = "OrderPage")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
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

	

   
}
