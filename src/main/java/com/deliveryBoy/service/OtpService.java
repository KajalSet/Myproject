package com.deliveryBoy.service;
import java.security.SecureRandom;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class OtpService {

    private static final SecureRandom random = new SecureRandom();
    private static final Map<String, OtpDetails> otpStorage = new HashMap<>();

    // Generate OTP
    public String generateOtp(String key) {
    	
    	System.out.println("Attempting to generate a random OTP.");
    	int otp = 100000 + random.nextInt(900000); // Generate 6-digit OTP
    	System.out.println("Generated OTP: " + otp);
    	String otpStr = String.valueOf(otp);
    	
        otpStorage.put(key, new OtpDetails(otpStr, LocalDateTime.now())); // Store OTP and generation time
        System.out.println("Updated OTP storage: " + otpStorage);
        
        return otpStr;
    }

    // Validate OTP
    public boolean isOtpValid(String key, String providedOtp) {
        OtpDetails details = otpStorage.get(key);

        if (details == null) {
            return false; // No OTP found for this key
        }

        LocalDateTime generatedTime = details.getGeneratedTime();
        if (LocalDateTime.now().isAfter(generatedTime.plusMinutes(5))) {
            otpStorage.remove(key); // Expire the OTP after 5 minutes
            return false;
        }

        boolean isValid = details.getOtp().equals(providedOtp);
        if (isValid) {
            otpStorage.remove(key); // Remove OTP after successful validation
        }

        return isValid;
    }
    
public void clearOTP(String key) {
		otpStorage.remove(key);

	}

    // Inner class to hold OTP details
    private static class OtpDetails {
        private final String otp;
        private final LocalDateTime generatedTime;

        public OtpDetails(String otp, LocalDateTime generatedTime) {
            this.otp = otp;
            this.generatedTime = generatedTime;
        }

        public String getOtp() {
            return otp;
        }

        public LocalDateTime getGeneratedTime() {
            return generatedTime;
        }
    }

	
}
