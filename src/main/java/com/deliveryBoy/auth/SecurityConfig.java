package com.deliveryBoy.auth;
 
import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


 
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
 
	@Autowired
	JdbcUserDetailsService customUserDetailsService;
 
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
 
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
 
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService);
	}
 
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors();
		http.csrf().disable().authorizeRequests()
				.antMatchers("/api/auth/authenticate", "/api/auth/posauthenticate", "/api/auth/signup",
						"/api/auth/verifyotp", "/api/auth/sendotp", "/api/auth/resendotp/**", "/api/auth/sendpdf",
						"/api/auth/signupByMobile","/api/vendor/login","/api/vendor/signup","/branch", "/api/auth/customer","/api/auth/refreshtoken",
						"/api/auth/verifyemail", "/api/panVerification/verify-pan-and-get-details/**",
						"/api/aadhaar/initiate", "/api/aadhaar/verify", "/api/auth/resendVerifyEmail", "/api/auth/resetPassword",
						"/api/auth/savePassword", "/", "/v3/api-docs", "/v3/api-docs/*", "/swagger-ui.html",
						"/swagger-ui/**", "/static/**", "/js/**", "/webjars/**", "/api/partners/create")
				.permitAll().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint);
 
		http.addFilterBefore(jwtRequestFilter, (Class<? extends Filter>) UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
 
}