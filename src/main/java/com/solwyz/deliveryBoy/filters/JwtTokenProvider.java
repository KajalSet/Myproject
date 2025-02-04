package com.solwyz.deliveryBoy.filters;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.solwyz.deliveryBoy.models.DeliveryBoy;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenProvider {

	@Value("${app.token.secret}")
	private String jwtSecret;

	@Value("${app.jwtExpirationMs}")
	private Long accessTokenValidity;

	@Value("${app.jwtRefreshExpirationMs}")
	private Long refreshTokenValidity;

	public String generateAccessToken(DeliveryBoy deliveryBoy) {
		return Jwts.builder().setSubject(deliveryBoy.getUsername()).claim("role", deliveryBoy.getRole().toString())
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
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

	// Get Username from Token
	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
}
