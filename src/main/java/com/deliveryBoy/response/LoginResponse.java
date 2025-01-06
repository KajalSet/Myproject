package com.deliveryBoy.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
	private boolean isAuthenticated;
    private boolean isMpinRequired;
    private String jwtToken;
}
