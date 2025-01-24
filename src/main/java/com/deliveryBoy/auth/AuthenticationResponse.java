package com.deliveryBoy.auth;

import java.util.UUID;

public class AuthenticationResponse {

    private String jwt;
    private String refreshToken;
    private UUID id;
    private String email;
    private String mobileNumber;

    // Constructor
    public AuthenticationResponse(String jwt, String refreshToken, UUID id, String email, String mobileNumber) {
        this.jwt = jwt;
        this.refreshToken = refreshToken;
        this.id = id;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    // Getters and Setters
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}