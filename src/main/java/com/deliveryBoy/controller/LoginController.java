package com.deliveryBoy.controller;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryBoy.entity.LoginEntity;
import com.deliveryBoy.request.LoginRequest;
import com.deliveryBoy.response.SuccessResponse;
import com.deliveryBoy.service.LoginService;



@RequestMapping(path="/api/auth")

@RestController
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
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
        	return loginService.login(loginRequest.getUserName(), loginRequest.getPassword());
        	
        }catch(Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + e.getMessage());
        }
        
    }//2.login
	//Token Generation is Pending 
	
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

	 
//	 @GetMapping("/api/sensitive/data")
//	 public ResponseEntity<?> getSensitiveData(@RequestHeader("UserId") String userId,
//	                                           @RequestHeader("Mpin") String mpin) {
//	     // Data retrieval code (only if MPIN is valid)
//	     return ResponseEntity.ok(sensitiveData);
//	 }

	
	 
	 
}
	


