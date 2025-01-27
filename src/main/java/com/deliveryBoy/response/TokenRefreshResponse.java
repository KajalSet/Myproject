package com.deliveryBoy.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TokenRefreshResponse {
	private String jwt;
	private String refreshToken;
	private String tokenType = "Bearer";

	public TokenRefreshResponse(String accessToken, String refreshToken) {
		this.jwt = accessToken;
		this.refreshToken = refreshToken;
	}
}
