package com.solwyz.deliveryBoy.filters;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.solwyz.deliveryBoy.models.DeliveryBoy;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("${app.token.secret}")
	private String jwtSecret;

	@Value("${app.jwtExpirationMs}")
	private Long accessTokenValidity;

	@Value("${app.jwtRefreshExpirationMs}")
	private Long refreshTokenValidity;

	public String generateAccessToken(DeliveryBoy deliveryBoy) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", deliveryBoy.getId()); // Add ID
		claims.put("address", deliveryBoy.getAddress()); // Add Address

		return Jwts.builder().setClaims(claims) // Set custom claims
				.setSubject(deliveryBoy.getUsername()) // Set username as subject
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
				.signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
	}

	// Generate Refresh Token
	public String generateRefreshToken(DeliveryBoy deliveryBoy) {
		return Jwts.builder().setSubject(deliveryBoy.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	// Validate Token
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	public Claims extractClaims(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
	}

	// Get Username from Token
	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public Long getUserIdFromToken(String token) {
		return extractClaims(token).get("id", Long.class);
	}

	public String getAddressFromToken(String token) {
		return extractClaims(token).get("address", String.class);
	}
}
