//package com.deliveryBoy.entity;
//
//import java.time.LocalDateTime;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import lombok.Data;
//@Data
//@Entity
//@Table(name="order_status_log")
//public class OrderStatusLog {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "order_id", nullable = false)
//    private OrderEntity order;
//
//    @Column(nullable = false)
//    private String previousStatus;
//
//    @Column(nullable = false)
//    private String newStatus;
//
//    @Column(nullable = false)
//    private LocalDateTime updatedAt;
//
//    @Column(nullable = false)
//    private String updatedBy;
//	
//	
//	
//	
//}
