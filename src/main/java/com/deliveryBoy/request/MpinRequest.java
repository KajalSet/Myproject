package com.deliveryBoy.request;






import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MpinRequest {


private String username;


private Integer mpin;

}
