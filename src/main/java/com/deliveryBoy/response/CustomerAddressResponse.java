//package com.deliveryBoy.response;
//
//import com.deliveryBoy.entity.Address;
//
//import lombok.Builder;
//import lombok.Data;
//
//@Data
//@Builder
//public class CustomerAddressResponse {
//	private String street;
//    private String city;
//    private String state;
//    private String zipCode;
//    private String country;
//
//
//
//	public static CustomerAddressResponse fromAddress(Address address) {
//		return CustomerAddressResponse.builder()
//	            .street(address.getStreet())
//	            .city(address.getCity())
//	            .state(address.getState())
//	            .zipCode(address.getZipCode())
//	            .country(address.getCountry())
//                .build();
//	}
//}
