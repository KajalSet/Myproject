package com.deliveryBoy.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private String orderId;
    private String date;
    private String store;
    private String deliveryLocation;
}