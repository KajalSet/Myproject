package com.deliveryBoy.response;




import java.time.LocalDate;




import com.deliveryBoy.entity.OrderEntity;
import com.deliveryBoy.enums.PaymentMode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
	private Long orderId;
    private String storeName;
    private CustomerAddressResponse deliveryAddress;
    private LocalDate orderDate;
    private String status;
    private PaymentMode paymentMode;
    
    
    public static OrderResponse fromEntity(OrderEntity order) {
    	
        OrderResponse orderResponse = OrderResponse.builder()
        .orderId(order.getId())
        .storeName(order.getStoreName())
        .deliveryAddress(CustomerAddressResponse.fromAddress(order.getDeliveryAddress()))
        .orderDate(order.getOrderDate())
        .status(order.getStatus().name())
        .paymentMode(order.getPaymentMode())
        .build();
        
        return orderResponse;
        
        }
    
    
    public String getFormattedAddress() {
        return deliveryAddress != null 
            ? String.format("%s, %s, %s, %s - %s",
                deliveryAddress.getStreet(),
                deliveryAddress.getCity(),
                deliveryAddress.getState(),
                deliveryAddress.getCountry(),
                deliveryAddress.getZipCode())
            : "Address not available"; 
    
    }
    
    
    
    
}
