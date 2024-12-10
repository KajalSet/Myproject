package com.deliveryBoy.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import com.deliveryBoy.entity.LoginEntity;
import com.deliveryBoy.repository.LoginRepository;


import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
public class SecurityConfig{
	@Autowired
    LoginRepository loginRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .authorizeRequests()
	                .antMatchers("/api/auth/**").permitAll()
	                .anyRequest().authenticated()
	            .and()
	            .formLogin()
	                .permitAll()
	            .and()
	            .httpBasic();
	        return http.build();
	    }

	    @Bean
	    public HttpFirewall allowDoubleSlashesFirewall() {
	        StrictHttpFirewall firewall = new StrictHttpFirewall();
	        firewall.setAllowUrlEncodedDoubleSlash(true); // Allows double slashes
	        return firewall;
	    }
	
	@Bean
	public UserDetailsService userDetailsService() {
		return username ->{
			Optional<LoginEntity>user=loginRepository.findByUserName(username);
			if(user.isPresent()) {
				return new UserPrincipal(user.get());
			}
			throw new UsernameNotFoundException("User not found");
					
		};
	}
	
	
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsService())
				.passwordEncoder(passwordEncoder)
				.and()
				.build();
		
		
	}

	
}
