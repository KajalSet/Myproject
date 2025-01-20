package com.deliveryBoy.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HomeRequest {
	
	private String orderId;
	
	private String customerName;
	
	private String deliveryAddress;
	
	private String contactNumber;
	
	private String status;
	

}
