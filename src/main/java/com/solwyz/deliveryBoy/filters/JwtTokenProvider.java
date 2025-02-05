package com.solwyz.deliveryBoy.filters;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
		return Jwts.builder().setSubject(deliveryBoy.getUsername()).claim("id", deliveryBoy.getId()) // Include
																										// DeliveryBoy
																										// ID
				.claim("address", deliveryBoy.getAssignedArea()) // Include Address
				.claim("role", deliveryBoy.getRole().name()) // Include Role
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

	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

		String username = claims.getSubject();
		String role = claims.get("role", String.class); // Extract role

		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));

		return new UsernamePasswordAuthenticationToken(username, null, authorities);
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
