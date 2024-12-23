package com.deliveryBoy.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class OrderEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeName;
    private String deliveryAddress;
    private LocalDate orderDate;
    private String status;
    private String otp;
    private String customerName;
    
      @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
      private List<NotificationEntity> notifications; 
      
      @ManyToOne
      private ProfileEntity assignedDeliveryBoy;
}
