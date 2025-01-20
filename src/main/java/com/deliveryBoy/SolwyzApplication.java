package com.deliveryBoy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication

public class SolwyzApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolwyzApplication.class, args);
	}
	
	
	@Bean(name = "neocorpBCryptPasswordEncoder")
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

}
