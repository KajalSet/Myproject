package com.deliveryBoy.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryBoy.auth.AuthenticationRequest;
import com.deliveryBoy.auth.AuthenticationResponse;
import com.deliveryBoy.auth.CurrentUser;
import com.deliveryBoy.auth.JdbcUserDetailsService;
import com.deliveryBoy.auth.JwtUtil;
import com.deliveryBoy.auth.RefreshToken;
import com.deliveryBoy.auth.RefreshTokenService;
import com.deliveryBoy.auth.User;
import com.deliveryBoy.auth.UserRepo;
import com.deliveryBoy.exception.RecordNotFoundException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JdbcUserDetailsService jdbcUserDetailsService;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	
	
	
	
	
	
	@PostMapping(value = "/authenticate", produces = "application/json")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

		System.out.println(authenticationRequest.toString());
		try {
			String jwt = "";
			Optional<User> oldUser = userRepo.findByUserName(authenticationRequest.getUsername());
			// JDBC Check BCrypt the password
			// log4j.debug("Inside JDBC check");
			CurrentUser userDetails = null;
			try {
				userDetails = jdbcUserDetailsService.loadUserByUsernameAndPass(authenticationRequest.getUsername(),
						authenticationRequest.getPassword());
				
			} catch (Exception e) {
				throw new RecordNotFoundException("Authentication Failed " + e.getMessage());
			}

			RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

			jwt = jwtTokenUtil.generateToken(userDetails);
			AuthenticationResponse response = new AuthenticationResponse(jwt, refreshToken.getToken() , userDetails.getId(),  // Add ID here
		            userDetails.getEmail(), 
		            userDetails.getMobileNumber());

			
			return ResponseEntity.ok(response);

		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

	}
	
	
	@PostMapping("/posauthenticate")
	public ResponseEntity<?> createPosAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

		try {
			String jwt = "";
			// Optional<User> oldUser =
			// userRepo.findByUserName(authenticationRequest.getUsername());

			// JDBC Check BCrypt the password
			// log4j.debug("Inside JDBC check");
			CurrentUser userDetails = null;
			try {
				userDetails = jdbcUserDetailsService.loadUserByUsernameAndPass(authenticationRequest.getUsername(),
						authenticationRequest.getPassword());
			} catch (Exception e) {
				throw new RecordNotFoundException("Authentication Failed " + e.getMessage());
			}

//			//StoreUser storeUser = storeUserRepo.findByUserId(userDetails.getId()).get();
//
//			if (storeUser.getCompany() == null) {
//				throw new GenericException("Company is not associated");
//			}
//			if (!storeUser.getCompany().isSubscriptionActive()) {
//				throw new GenericException("Subscription Inactive");
//			}

			RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

			jwt = jwtTokenUtil.generateToken(userDetails);
			AuthenticationResponse response = new AuthenticationResponse(jwt, refreshToken.getToken(), userDetails.getId(),  // Add ID here
		            userDetails.getEmail(), 
		            userDetails.getMobileNumber());

			return ResponseEntity.ok(response);

		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

	}
	
//	@PostMapping(value = "/authenticate", produces = "application/json")
//	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
//			throws Exception {
//
//		System.out.println(authenticationRequest.toString());
//		try {
//			String jwt = "";
//			Optional<User> oldUser = userRepo.findByUserName(authenticationRequest.getUsername());
//			// JDBC Check BCrypt the password
//			// log4j.debug("Inside JDBC check");
//			CurrentUser userDetails = null;
//			try {
//				userDetails = jdbcUserDetailsService.loadUserByUsernameAndPass(authenticationRequest.getUsername(),
//						authenticationRequest.getPassword());
//				
//			} catch (Exception e) {
//				throw new RecordNotFoundException("Authentication Failed " + e.getMessage());
//			}
//
//			RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
//
//			jwt = jwtTokenUtil.generateToken(userDetails);
//			AuthenticationResponse response = new AuthenticationResponse(jwt, refreshToken.getToken());
//
//			return ResponseEntity.ok(response);
//
//		} catch (BadCredentialsException e) {
//			throw new Exception("Incorrect username or password", e);
//		}
//
//	}

	
	
	
	
	
	
	
	
	
	
	
}
