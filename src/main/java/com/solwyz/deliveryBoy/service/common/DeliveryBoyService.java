package com.solwyz.deliveryBoy.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.solwyz.deliveryBoy.Enum.Role;
import com.solwyz.deliveryBoy.filters.JwtTokenProvider;
import com.solwyz.deliveryBoy.models.DeliveryBoy;
import com.solwyz.deliveryBoy.pojo.request.AuthenticationRequest;
import com.solwyz.deliveryBoy.pojo.request.DeliveryBoyDTO;
import com.solwyz.deliveryBoy.pojo.request.RefreshTokenRequest;
import com.solwyz.deliveryBoy.pojo.response.AuthenticationResponse;
import com.solwyz.deliveryBoy.repo.common.DeliveryBoyRepository;

@Service
public class DeliveryBoyService {

	@Autowired
	private DeliveryBoyRepository deliveryBoyRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// Register new Delivery Boy (Admin)
	public DeliveryBoy registerDeliveryBoy(DeliveryBoyDTO deliveryBoyDTO) {
		DeliveryBoy deliveryBoy = new DeliveryBoy();
		deliveryBoy.setUsername(deliveryBoyDTO.getUsername());
		deliveryBoy.setPassword(passwordEncoder.encode(deliveryBoyDTO.getPassword())); // Hash the password
		deliveryBoy.setRole(Role.DELIVERY_BOY);
		return deliveryBoyRepository.save(deliveryBoy);
	}

	// Authenticate user (Admin or Delivery Boy)
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		DeliveryBoy deliveryBoy = deliveryBoyRepository.findByUsername(request.getUsername());

		if (deliveryBoy == null || !passwordEncoder.matches(request.getPassword(), deliveryBoy.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}

		// Check if the MPIN is set, if not, ask for MPIN setup
		if (deliveryBoy.getMpin() == null) {
			throw new RuntimeException("Please set your MPIN.");
		}

		// If MPIN is set, validate the MPIN
		if (!passwordEncoder.matches(request.getMpin(), deliveryBoy.getMpin())) {
			throw new RuntimeException("Invalid MPIN.");
		}

		String accessToken = jwtTokenProvider.generateAccessToken(deliveryBoy);
		String refreshToken = jwtTokenProvider.generateRefreshToken(deliveryBoy);

		return new AuthenticationResponse(accessToken, refreshToken);
	}

	// Refresh token
	public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String refreshToken = refreshTokenRequest.getRefreshToken();

		// Check if refresh token is valid
		if (!jwtTokenProvider.validateToken(refreshToken)) {
			throw new RuntimeException("Invalid refresh token.");
		}

		String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
		DeliveryBoy deliveryBoy = deliveryBoyRepository.findByUsername(username);

		if (deliveryBoy == null) {
			throw new RuntimeException("User not found");
		}

		String newAccessToken = jwtTokenProvider.generateAccessToken(deliveryBoy);
		String newRefreshToken = jwtTokenProvider.generateRefreshToken(deliveryBoy);

		return new AuthenticationResponse(newAccessToken, newRefreshToken);
	}

	// Set MPIN for the DeliveryBoy
	public String setMpin(String username, String mpin) {
		DeliveryBoy deliveryBoy = deliveryBoyRepository.findByUsername(username);

		if (deliveryBoy == null) {
			throw new RuntimeException("User not found");
		}

		deliveryBoy.setMpin(passwordEncoder.encode(mpin)); // Hash the MPIN
		deliveryBoyRepository.save(deliveryBoy);
		return "MPIN set successfully!";
	}
}
