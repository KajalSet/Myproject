package com.deliveryBoy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryBoy.auth.JwtUtil;
import com.deliveryBoy.entity.LoginEntity;
import com.deliveryBoy.repository.LoginRepository;
import com.deliveryBoy.request.LoginRequest;
import com.deliveryBoy.request.LoginResponse;
import com.deliveryBoy.service.LoginService;
import com.deliveryBoy.service.OtpService;

@RequestMapping(path="/api/auth")

@RestController
public class LoginController {
	
	@Autowired
	LoginService loginService;

    @Autowired
    private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;
	
	private  LoginRepository loginRepository;
    private  OtpService otpService;

   
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody LoginRequest loginRequest){
		
		try {
			String result=loginService.register(loginRequest);
			
			if(result.equals("Username already exists")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
			}
			return ResponseEntity.ok(result);
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
		}
		
	}//1.register
	
	
	 @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		 try {
		        
		        
		        // Perform authentication
		        boolean isAuthenticated = loginService.authenticate(loginRequest.getUserName(), loginRequest.getPassword());
		       

		        if (isAuthenticated) {
		            // Generate JWT token
		            String token = jwtUtil.generateToken(loginRequest.getUserName());
		          

		            // Return token in the response
		            return ResponseEntity.ok(new LoginResponse(token));
		        } else {
		          
		            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		        }
		    } catch (Exception e) {
		        System.out.println("Debug: Exception occurred: " + e.getMessage());
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + e.getMessage());
		    }
	 }
	 
	
	 @GetMapping("/isMpinCreated")
	    public ResponseEntity<Boolean> isMpinCreated(@RequestParam String username) {
	        boolean isCreated = loginService.isMpinCreated(username);
	        return ResponseEntity.ok(isCreated);
	    }
	 
	
	 @PostMapping("/createMpin")
	    public ResponseEntity<String> createMpin(@RequestParam Long id, @RequestParam Integer mpin) {
	        try {
	        	
        	
	            LoginEntity loginEntity = loginService.createMpin(id, mpin);
	            
	            return ResponseEntity.ok("MPIN created successfully");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	            		.body(e.getMessage());
	        }
	    }
	   
	   
	   
  
	
   
   
	 @PostMapping("/resetMpin")
	    public ResponseEntity<String> resetMpin(@RequestParam Long id, @RequestParam Integer newMpin) {
	        try {
	            loginService.resetMpin(id, newMpin);
	            return ResponseEntity.ok("MPIN reset successfully");
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        }
	    }
   
   //5.Forgot Password is pending
   
	 @PostMapping("/validateMpin")
	 public ResponseEntity<String> validateMpin(@RequestParam Long id, @RequestParam Integer mpin) {
	     try {
	         boolean isValid = loginService.validateMpin(id, mpin);
	         if (isValid) {
	             return ResponseEntity.ok("MPIN is valid");
	         } else {
	             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid MPIN");
	         }
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	     }
	 }

	 //forgot password
	 @PostMapping("/request-otp")
	 public ResponseEntity<String> requestOtp(@RequestParam String mobileNumber) {
	     try {
	    	    // Log the mobile number being processed
	         System.out.println("Generating OTP for mobile number: " + mobileNumber);

	         if (mobileNumber == null || mobileNumber.trim().isEmpty()) {
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mobile number cannot be empty.");
	         }
	       
	         String otp = otpService.generateOtp(mobileNumber);
	         System.out.println("Generated OTP: " + otp);
	         
	         // Send the OTP via email or SMS (implementation omitted)
	         return ResponseEntity.ok("OTP sent successfully.");
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating OTP.");
	     }
	 }
	 
	 @PostMapping("/verify-otp")
	    public ResponseEntity<String> verifyOtp(@RequestParam String mobileNumber, @RequestParam String otp) {
	        try {
	            // Step 1: Validate OTP
	            boolean isValid = otpService.isOtpValid(mobileNumber, otp);

	            if (isValid) {
	                // Step 2: Proceed with password reset logic (e.g., allow user to set a new password)
	                return ResponseEntity.ok("OTP verified successfully. You can now reset your password.");
	            } else {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP.");
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error verifying OTP.");
	        }
	    }
	










	 
//	  @PostMapping("/forgot-password")
//	    public ResponseEntity<String> sendOTP(@RequestParam String phoneNumber) {
//	        String otp = otpService.generateOtp(phoneNumber);
//	        sendOtpViaSms(phoneNumber, otp); // Send OTP via SMS (Twilio or other service)
//
//	        return new ResponseEntity<>("OTP sent successfully.", HttpStatus.OK);
//	    }
	  
	  
	  
//	  @PostMapping("/reset-password")
//	    public ResponseEntity<String> resetPassword(@RequestParam String phoneNumber,
//	                                                @RequestParam String otp,
//	                                                @RequestParam String newPassword) {
//
//	        boolean isValidOtp = otpService.isOtpValid(phoneNumber, otp);
//	        if (!isValidOtp) {
//	            return new ResponseEntity<>("Invalid or expired OTP.", HttpStatus.BAD_REQUEST);
//	        }
//
//	        // Update the user's password
//	        boolean isPasswordUpdated = loginService.updatePassword(phoneNumber, newPassword);
//	        if (isPasswordUpdated) {
//	            otpService.clearOTP(phoneNumber); // Clear OTP after successful reset
//	            return new ResponseEntity<>("Password reset successful.", HttpStatus.OK);
//	        } else {
//	            return new ResponseEntity<>("Error updating password.", HttpStatus.INTERNAL_SERVER_ERROR);
//	        }
//	    }
	  
//	  private void sendOtpViaSms(String phoneNumber, String otp) {
//	        // Twilio or other service implementation
//	        System.out.println("Sending OTP " + otp + " to " + phoneNumber);
//	    }ader("UserId") String userId,
//	                                           @RequestHeader("Mpin") String mpin) {
//	     // Data retrieval code (only if MPIN is valid)
//	     return ResponseEntity.ok(sensitiveData);
//	 }
	 
//	 @GetMapping("/api/sensitive/data")
//	 public ResponseEntity<?> getSensitiveData(@RequestHe

	
	 
	 

}
	


