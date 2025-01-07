package com.deliveryBoy.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
	
@NotNull
@Size(min=3,max=30)
private String userName;
@NotNull(message="Password is mandatory")
@Size(min=9)
private String password;
@Size(min=10)
private String mobileNumber;
@Email 
private String email; 
}
