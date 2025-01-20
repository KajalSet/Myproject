package com.deliveryBoy.auth;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RefreshTokenService {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	private UserRepo userRepository;


	@Value("${app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;

	
	public Optional<RefreshToken> findByToken(String token) {
		
		return refreshTokenRepository.findByToken(token);
		
	}
	
	public RefreshToken createRefreshToken(UUID userId) {
		RefreshToken refreshToken = new RefreshToken();

		refreshToken.setUser(userRepository.findById(userId).get());
		Date expDate = DateUtils.addMilliseconds(new Date(), refreshTokenDurationMs.intValue());
		refreshToken.setExpiryDate(expDate);
		refreshToken.setToken(UUID.randomUUID().toString());

		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}
	  
	
	
	
	
	
}
