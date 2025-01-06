//package com.deliveryBoy.entity;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//import lombok.Builder;
//import lombok.Data;
//
//@Data
//@Entity
//@Builder
//public class Address {
//	 @Id
//	    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	    private Long id;
//
//	    private String street;
//	    private String city;
//	    private String state;
//	    private String zipCode;
//	    private String country;
//
//	    public String toQueryString() {
//	        return street + ", " + city + ", " + state + " " + zipCode + ", " + country;
//	    }
//}
