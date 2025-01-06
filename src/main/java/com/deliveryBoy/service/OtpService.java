package com.deliveryBoy.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class OtpService {
	private static final SecureRandom random = new SecureRandom();

    public String generateOtp() {
        int otp = 100000 + random.nextInt(900000); // Generate 6-digit OTP
        return String.valueOf(otp);
    }

    public boolean isOtpValid(String generatedOtp, String providedOtp, LocalDateTime generatedTime) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(generatedTime.plusMinutes(5))) { // OTP is valid for 5 minutes
            return false;
        }
        return generatedOtp.equals(providedOtp);
    }
}

