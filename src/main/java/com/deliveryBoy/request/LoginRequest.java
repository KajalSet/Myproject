package com.deliveryBoy.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Data
@Builder
public class LoginRequest {
	
@NotNull
@Size(min=3,max=30)
private String userName;
@NotNull(message="Password is mandatory")
@Size(min=9)
private String password;
}
