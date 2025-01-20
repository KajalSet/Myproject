package com.deliveryBoy.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.deliveryBoy.enums.OrderStatus;
import com.deliveryBoy.enums.PaymentMode;
import com.deliveryBoy.enums.PaymentStatus;

import lombok.Builder;
import lombok.Data;


@Data
@Entity
@Builder
public class OrderEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeName;
    private LocalDate orderDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
    
    private String otp;
    private String customerName;
    
    private LocalDateTime otpGeneratedTime; 
    
    @ManyToOne(cascade = CascadeType.ALL)
      private Address deliveryAddress;
    
      @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
      private List<NotificationEntity> notifications; 
      
      @ManyToOne
      private ProfileEntity assignedDeliveryBoy;
      
      @Enumerated(EnumType.STRING)
      private PaymentMode paymentMode;
      
      @Enumerated(EnumType.STRING)
      private PaymentStatus paymentStatus;
}
