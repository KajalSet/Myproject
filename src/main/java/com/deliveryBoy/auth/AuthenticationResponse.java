package com.deliveryBoy.auth;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthenticationResponse {

	private String jwt;

	private String refreshToken;

	private String tokenType = "Bearer";

	public AuthenticationResponse(String jwt, String refreshToken) {
		super();
		this.jwt = jwt;
		this.refreshToken = refreshToken;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
	
}
