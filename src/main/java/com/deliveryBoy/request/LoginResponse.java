package com.deliveryBoy.request;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private Long id;
    private String username;
 

    // Constructor
    public LoginResponse(String token, Long id, String username) {
        this.token = token;
        this.id = id;
        this.username = username;
       
    }


  
}
