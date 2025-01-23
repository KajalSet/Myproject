package com.deliveryBoy.auth;

import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtUtil {

	@Value("${app.token.secret}")
	private String SECRET_KEY;

	@Value("${app.jwtExpirationMs}")
	private Long jwtExpirationMs;
	
	
	public String extractUsername(String token) throws SignatureException {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) throws SignatureException {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws SignatureException {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) throws SignatureException {
		try {
			Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
			return claims;
		} catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
			
			e.printStackTrace();
		}
		return null;
	}
//	private Claims extractAllClaims(String token) {
//		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//	}

	private boolean isTokenExpired(String token) throws SignatureException {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(CurrentUser userDetails) {
		Map<String, Object> claims = new HashMap<>();
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		List<String> rs = new ArrayList<String>();
		for (GrantedAuthority authority : authorities) {
			rs.add(authority.getAuthority());
		}
		String roles = StringUtils.join(rs, ",");
		claims.put("roles", roles);
		claims.put("userDetails", userDetails);
		String token = createToken(claims, userDetails.getUsername());
		System.out.println("Token " + token);
		return token;
	}

	private String createToken(Map<String, Object> claims, String subject) {

		Date expDate = DateUtils.addMilliseconds(new Date(), jwtExpirationMs.intValue());

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(expDate).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public boolean validateToken(String token, UserDetails userDetails) throws SignatureException {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
