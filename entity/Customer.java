//package com.deliveryBoy.entity;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Table(name = "customers")
//public class Customer {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//    private String email;
//    private String phoneNumber;
//
//    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
//    private List<OrderEntity> orders;
//
//    // Getters and setters
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public List<OrderEntity> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<OrderEntity> orders) {
//        this.orders = orders;
//    }
//}
