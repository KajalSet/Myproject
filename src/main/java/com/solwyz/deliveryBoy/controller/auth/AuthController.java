package com.solwyz.deliveryBoy.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solwyz.deliveryBoy.Exceptions.AuthenticationException;
import com.solwyz.deliveryBoy.filters.JwtTokenProvider;
import com.solwyz.deliveryBoy.models.DeliveryBoy;
import com.solwyz.deliveryBoy.pojo.request.AuthenticationRequest;
import com.solwyz.deliveryBoy.pojo.request.RefreshTokenRequest;
import com.solwyz.deliveryBoy.pojo.response.AuthenticationResponse;
import com.solwyz.deliveryBoy.service.common.DeliveryBoyService;

import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
@Tag(name = "User Authentication", description = "APIs for User Authentication related operations")
public class AuthController {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private DeliveryBoyService deliveryBoyService;

	// Register Delivery Boy (Admin only)
	@PostMapping("/register")
	public DeliveryBoy registerDeliveryBoy(@RequestBody DeliveryBoy deliveryBoy) {
		return deliveryBoyService.registerDeliveryBoy(deliveryBoy);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authRequest) {
		AuthenticationResponse response = deliveryBoyService.authenticate(authRequest);
		return ResponseEntity.ok(response);
	}

	// Handle AuthenticationException
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	// Refresh Token (if expired)
	@PostMapping("/refresh-token")
	public AuthenticationResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		return deliveryBoyService.refreshToken(refreshTokenRequest);
	}

	// Set MPIN (Delivery Boy only)
	@PostMapping("/set-mpin")
	public String setMpin(@RequestParam String username, @RequestParam String mpin) {
		return deliveryBoyService.setMpin(username, mpin);
	}
}
