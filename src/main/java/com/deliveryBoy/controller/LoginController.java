package com.deliveryBoy.controller;


import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryBoy.request.LoginRequest;
import com.deliveryBoy.request.MpinRequest;
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
		
	}
	
	
	@PostMapping("/login")
    public ResponseEntity<SuccessResponse> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUserName();
        String password = loginRequest.getPassword();

        
        
        boolean isValidUser = loginService.login(username, password);

        if (isValidUser) {
        	if(loginService.isMpinCreated(loginRequest.getUserName())) {
        		return ResponseEntity.ok(SuccessResponse.builder()
        			    .message("Login successful. MPIN is already set.")
        			    .status(HttpStatus.OK)
        			    .timeStamp(LocalDateTime.now())
        			    .build());

        	
        	}else {
        		return ResponseEntity.ok(SuccessResponse.builder()
                        .message("Login successful. Please set your MPIN.")
                        .status(HttpStatus.OK)
                        .timeStamp(LocalDateTime.now())
                        .build());
            }
            
        }else {
        	 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(SuccessResponse.builder()
                     .message("Invalid username or password")
                     .status(HttpStatus.UNAUTHORIZED)
                     .build());
            }
        
        
    }//login
	
	@PostMapping("/create-mpin")
	public ResponseEntity<String> createMpin( @Valid @RequestBody MpinRequest mpinRequest) {
		
		String mpin = loginService.createMpin(mpinRequest.getUsername(),mpinRequest.getMpin());
		
		return ResponseEntity.ok(mpin);
	}//create mpin
		
		
		
}
	


