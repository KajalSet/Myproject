package com.deliveryBoy.request;

public class LoginResponse {
	 private String token;

	    // Constructor
	    public LoginResponse(String token) {
	        this.token = token;
	    }

	    // Getter for token
	    public String getToken() {
	        return token;
	    }

	    // Setter for token
	    public void setToken(String token) {
	        this.token = token;
	    }
}
