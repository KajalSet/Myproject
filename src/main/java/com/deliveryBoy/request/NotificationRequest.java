package com.deliveryBoy.request;

import lombok.Data;

@Data
public class NotificationRequest {

 private String orderId;
 private Long deliveryBoyId;
 private String customerName;
 private String orderStatus;
 
 private Long notificationId;
 private Boolean read;
	
}
