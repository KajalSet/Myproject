package com.solwyz.deliveryBoy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.solwyz.deliveryBoy.filters.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/api/auth/login", "/api/auth/register").permitAll() // Allow
																													// login
																													// and
																													// register
																													// without
																													// authentication
				.antMatchers("/api/deliveryboys/**").hasRole("ADMIN") // Admin can manage delivery boys
				.antMatchers("/api/orders/**").hasRole("DELIVERY_BOY") // DeliveryBoy can access their orders
				.anyRequest().authenticated() // All other requests need authentication
				.and().addFilterBefore(new JwtAuthFilter(), UsernamePasswordAuthenticationFilter.class); // Custom JWT
																											// Authentication
																											// filter
	}

	@Override
	protected UserDetailsService userDetailsService() {
		return super.userDetailsService();
	}
}
