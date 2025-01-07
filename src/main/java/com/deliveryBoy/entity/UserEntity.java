//package com.deliveryBoy.entity;
//
//import java.util.Collection;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//import lombok.Data;
//
//@Data
//@Entity
//@Table(name = "User")
//public class UserEntity {
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, name = "UserName", unique = true)
//    private String userName;
//
//    @Column(name = "Password", nullable = false)
//    private String password;
//
//    @Column(name = "isActive", nullable = false)
//    private boolean isActive;
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_roles", 
//               joinColumns = @JoinColumn(name = "user_id"), 
//               inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Collection<RoleEntity> roles;
//    
//    @OneToOne(mappedBy = "user")
//    private LoginEntity loginEntity;
//}
